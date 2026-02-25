package org.example.demo.exceptions;

public class UserWithEmailAlreadyExistsException extends RuntimeException {
    public UserWithEmailAlreadyExistsException(String email) {
        super("User with email: " + email + " already exists");
    }
}
