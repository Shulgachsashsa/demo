package org.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.dto.request.SignupRequest;
import org.example.demo.dto.request.VerifySignupRequest;
import org.example.demo.dto.response.JwtAuthenticationResponse;
import org.example.demo.dto.response.SignupResponse;
import org.example.demo.entity.User;
import org.example.demo.entity.enums.Provider;
import org.example.demo.entity.enums.Role;
import org.example.demo.exceptions.*;
import org.example.demo.modelsRedis.RegistrationData;
import org.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserService userService;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MailCodeService mailCodeService;
    private final UserRepository userRepository;
    private final RedisAuthService redisAuthService;
    private final RatingOfPassengerService ratingOfPassengerService;
    private final RefreshTokenService refreshTokenService;

    public SignupResponse signup(SignupRequest request) {
        log.info("Initiating registration for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserWithEmailAlreadyExistsException(request.getEmail());
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserWithUsernameAlreadyExistsException(request.getUsername());
        }

        String code = mailCodeService.generateCode();
        log.debug("Generated code for {}: {}", request.getEmail(), code);

        RegistrationData registrationData = new RegistrationData(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                code
        );

        redisAuthService.addInitiateRegistrationData(request.getEmail(), registrationData);

        CompletableFuture.runAsync(() -> {
            try {
                mailSenderService.send(request.getEmail(), code);
                log.info("Verification code sent to: {}", request.getEmail());
            } catch (Exception e) {
                log.error("Failed to send email to: {}", request.getEmail(), e);
            }
        });

        return new SignupResponse("Verification code sent to your email", request.getEmail());
    }

    public JwtAuthenticationResponse verifyAndComplete(VerifySignupRequest request) {
        log.info("Complete registration for email: {}", request.getEmail());

        RegistrationData data = redisAuthService.getRegistrationData(request.getEmail());

        if (data == null)
            throw new RegistrationExpiredOrNotFoundException("Registration expired or not found. Please start over.");

        if (redisAuthService.checkMaxAttempts(data)) {
            redisAuthService.deleteRegistrationData(request.getEmail());
            throw new TooManyFailedAttemptsExceptions("Too many failed attempts. Please start over.");
        }

        if (!redisAuthService.equalsCode(request.getCode(), request.getEmail())) {
            redisAuthService.addAttempts(request.getEmail());
            throw new InvalidCodeException("Invalid code. Attempts left: " +
                    redisAuthService.getMaxAttempts(request.getEmail()));
        }

        User user = User.builder()
                .username(data.getUsername())
                .password(data.getPassword())
                .role(Role.ROLE_USER)
                .email(data.getEmail())
                .provider(Provider.EMAIL)
                .build();

        userService.create(user);
        ratingOfPassengerService.createRatingForNewPassenger(user);

        log.info("User created successfully: {}", user.getEmail());
        log.info("Created rating for passenger with email: {}", user.getEmail());
        redisAuthService.deleteRegistrationData(request.getEmail());

        return JwtAuthenticationResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .refreshToken(String.valueOf(refreshTokenService.createRefreshToken(user.getId()).getToken()))
                .build();
    }

    public void resendCode(String email) {
        log.info("Resending code for email: {}", email);

        RegistrationData data = redisAuthService.getRegistrationData(email);

        if (data == null)
            throw new RegistrationExpiredOrNotFoundException("Registration not found. Please start over");

        String newCode = mailCodeService.generateCode();

        data.setVerificationCode(newCode);
        data.setAttempts(0);
        redisAuthService.addInitiateRegistrationData(email, data);

        mailSenderService.send(email, newCode);
        log.info("New code sent to: {}", email);
    }

}
