package org.example.demo.exceptions;

public class UserWithEmailAlreadyExist extends RuntimeException {
    public UserWithEmailAlreadyExist(String email) {
        super("User with email: " + email + " already exists");
    }
}
