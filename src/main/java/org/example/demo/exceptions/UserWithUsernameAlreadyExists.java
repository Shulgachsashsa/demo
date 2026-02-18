package org.example.demo.exceptions;

public class UserWithUsernameAlreadyExists extends RuntimeException {
    public UserWithUsernameAlreadyExists(String username) {
        super("User with username: " + username + " already exists");
    }
}
