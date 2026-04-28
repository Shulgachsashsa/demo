package org.example.demo.exceptions;

public class TimeMoreThanTripException extends RuntimeException {
    public TimeMoreThanTripException(String message) {
        super(message);
    }
}
