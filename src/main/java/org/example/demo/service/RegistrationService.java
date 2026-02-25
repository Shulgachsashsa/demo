package org.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.dto.request.SignupRequest;
import org.example.demo.dto.request.VerifySignupRequest;
import org.example.demo.dto.response.JwtAuthenticationResponse;
import org.example.demo.dto.response.SignupResponse;
import org.example.demo.entity.User;
import org.example.demo.entity.enums.Role;
import org.example.demo.exceptions.*;
import org.example.demo.modelsRedis.RegistrationData;
import org.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserService userService;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MailCodeService mailCodeService;
    private final UserRepository userRepository;

    @Value("${spring.cache.redis.key-prefix}")
    private String KEY_PREFIX;

    @Value("${spring.cache.redis.time-to-live}")
    private long TTL_MINUTES;

    @Value("${spring.cache.redis.max-attempts}")
    private int MAX_ATTEMPTS;

    public SignupResponse signup(SignupRequest request) {
        log.info("Initiating registration for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserWithEmailAlreadyExistsException(request.getEmail());
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserWithUsernameAlreadyExistsException(request.getUsername());
        }

        String code = mailCodeService.generateCode(request.getEmail());
        log.debug("Generated code for {}: {}", request.getEmail(), code);

        RegistrationData registrationData = new RegistrationData(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                code
        );

        String key = KEY_PREFIX + request.getEmail();
        redisTemplate.opsForValue().set(key, registrationData, TTL_MINUTES, TimeUnit.MINUTES);

        CompletableFuture.runAsync(() -> {
            try {
                mailSenderService.send(request.getEmail(), code);
                log.info("Verification code sent to: {}", request.getEmail());
            } catch (Exception e) {
                log.error("Failed to send email to: {}", request.getEmail(), e);
            }
        });

        return new SignupResponse(
                "Verification code sent to your email",
                request.getEmail(),
                (int) TTL_MINUTES);
    }

    public JwtAuthenticationResponse verifyAndComplete(VerifySignupRequest request) {
        log.info("Complete registration for email: {}", request.getEmail());

        String key = KEY_PREFIX + request.getEmail();
        RegistrationData data = (RegistrationData) redisTemplate.opsForValue().get(key);
        if (data == null)
            throw new RegistrationExpiredOrNotFoundException("Registration expired or not found. Please start over.");

        if (data.getAttempts() >= MAX_ATTEMPTS) {
            redisTemplate.delete(key);
            throw new TooManyFailedAttemptsExceptions("Too many failed attempts. Please start over.");
        }

        if (!data.getVerificationCode().equals(request.getCode())) {
            data.setAttempts(data.getAttempts() + 1);
            redisTemplate.opsForValue().set(key, data, TTL_MINUTES, TimeUnit.MINUTES);
            int attemptsLeft = MAX_ATTEMPTS - data.getAttempts();
            throw new InvalidCodeException("Invalid code. Attempts left: " + attemptsLeft);
        }

        User user = User.builder()
                .username(data.getUsername())
                .password(data.getPassword())
                .role(Role.ROLE_USER)
                .enabled(true)
                .email(data.getEmail())
                .build();

        userService.create(user);
        log.info("User created successfully: {}", user.getEmail());
        redisTemplate.delete(key);

        return new JwtAuthenticationResponse(jwtService.generateToken(user));
    }

    public void resendCode(String email) {
        log.info("Resending code for email: {}", email);

        String key = KEY_PREFIX + email;
        RegistrationData data = (RegistrationData) redisTemplate.opsForValue().get(key);

        if (data == null)
            throw new RegistrationExpiredOrNotFoundException("Registration not found. Please start over");

        String newCode = mailCodeService.generateCode(email);
        data.setVerificationCode(mailCodeService.generateCode(newCode));
        data.setAttempts(0);
        redisTemplate.opsForValue().set(key, data, TTL_MINUTES, TimeUnit.MINUTES);
        mailSenderService.send(email, newCode);
        log.info("New code sent to: {}", email);
    }
}
