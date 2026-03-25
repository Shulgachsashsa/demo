package org.example.demo.exceptions;

public class NoAuthenticationUserFoundException extends RuntimeException {
    public NoAuthenticationUserFoundException(String message) {
        super(message);
    }
}
