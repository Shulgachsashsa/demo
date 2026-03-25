package org.example.demo.exceptions;

public class UserAlreadyIsDriverException extends RuntimeException {
    public UserAlreadyIsDriverException(String message) {
        super(message);
    }
}
