package org.example.demo.exceptions;

public class DriverNotExistsException extends RuntimeException {
    public DriverNotExistsException(String message) {
        super(message);
    }
}
