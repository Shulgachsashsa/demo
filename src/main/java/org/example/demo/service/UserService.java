package org.example.demo.service;

import org.example.demo.entity.User;
import org.example.demo.exceptions.UserWithEmailAlreadyExistsException;
import org.example.demo.exceptions.UserWithUsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.example.demo.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) { return userRepository.findByEmail(email); }

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserWithUsernameAlreadyExistsException(user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserWithEmailAlreadyExistsException(user.getEmail());
        }
        return save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
}
