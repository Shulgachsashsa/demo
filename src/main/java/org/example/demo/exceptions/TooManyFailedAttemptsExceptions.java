package org.example.demo.exceptions;

public class TooManyFailedAttemptsExceptions extends RuntimeException {
    public TooManyFailedAttemptsExceptions(String message) {
        super(message);
    }
}
