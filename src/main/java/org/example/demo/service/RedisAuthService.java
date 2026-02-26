package org.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.SignupRequest;
import org.example.demo.modelsRedis.RegistrationData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisAuthService {

    @Value("${spring.cache.redis.key-prefix}")
    private String KEY_PREFIX;

    @Value("${spring.cache.redis.time-to-live}")
    private long TTL_MINUTES;

    @Value("${spring.cache.redis.max-attempts}")
    private int MAX_ATTEMPTS;

    private final RedisTemplate<String, Object> redisTemplate;

    public void addInitiateRegistrationData(String key, Object object) {
        redisTemplate.opsForValue().set(key + KEY_PREFIX, object, TTL_MINUTES, TimeUnit.MINUTES);
    }

    public RegistrationData getRegistrationData(String key) {
        return (RegistrationData) redisTemplate.opsForValue().get(key + KEY_PREFIX);
    }

    public boolean checkMaxAttempts(RegistrationData data) {
        return data.getAttempts() >= MAX_ATTEMPTS;
    }

    public void deleteRegistrationData(String key) {
        redisTemplate.delete(key + KEY_PREFIX);
    }

    public void addAttempts(String key) {
        RegistrationData data = getRegistrationData(key);
        data.setAttempts(data.getAttempts() + 1);
        addInitiateRegistrationData(key, data);
    }

    public boolean equalsCode(String codeRequest, String key) {
       RegistrationData data = getRegistrationData(key);
       return data.getVerificationCode().equals(codeRequest);
    }

    public int getMaxAttempts(String key) {
        RegistrationData data = getRegistrationData(key);
        return data.getAttempts();
    }


}
