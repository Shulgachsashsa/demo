package org.example.demo.exceptions;

public class RegistrationExpiredOrNotFoundException extends RuntimeException {
    public RegistrationExpiredOrNotFoundException(String message) {
        super(message);
    }
}
