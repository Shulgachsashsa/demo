package org.example.demo.exceptions;

public class RefreshTokenIsNotDBException extends RuntimeException {
    public RefreshTokenIsNotDBException(String message) {
        super(message);
    }
}
