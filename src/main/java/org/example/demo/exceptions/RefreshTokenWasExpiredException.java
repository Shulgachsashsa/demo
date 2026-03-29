package org.example.demo.exceptions;

public class RefreshTokenWasExpiredException extends RuntimeException {
    public RefreshTokenWasExpiredException(String message) {
        super(message);
    }
}
