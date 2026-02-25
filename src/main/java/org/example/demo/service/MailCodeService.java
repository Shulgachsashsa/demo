package org.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MailCodeService {

    private final Map<String, String> codeStorage = new ConcurrentHashMap<>();

    private final Map<String, Long> expirationStorage = new ConcurrentHashMap<>();

    public String generateCode(String email) {
        String code = new Random()
                .ints(48, 58)
                .limit(6)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        codeStorage.put(email, code);
        expirationStorage.put(email, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10));
        return code;
    }

    public boolean isValid(String email, String code) {
        Long exp = expirationStorage.get(email);
        return exp != null && System.currentTimeMillis() <= exp && code.equals(codeStorage.get(email));
    }

    public void invalidate(String email) {
        codeStorage.remove(email);
        expirationStorage.remove(email);
    }


}
