package org.example.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.entity.RefreshToken;
import org.example.demo.exceptions.RefreshTokenWasExpiredException;
import org.example.demo.repository.RefreshTokenRepository;
import org.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${spring.token.signing.refresh}")
    private int REFRESH_TTL_DAYS;

    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        refreshTokenRepository.deleteRefreshTokenByUserId(userId);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findUserById(userId))
                .expiryDate(Instant.now().plus(Duration.ofDays(REFRESH_TTL_DAYS)))
                .token(UUID.randomUUID().toString())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenWasExpiredException("Refresh token was expired");
        }

        return token;
    }
}
