package org.example.demo.service;

import org.example.demo.entity.User;
import org.example.demo.entity.enums.Role;
import org.example.demo.exceptions.UserWithEmailAlreadyExistsException;
import org.example.demo.exceptions.UserWithUsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.demo.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    public User createOAuth2User(String username, String email, String googleId) {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .role(Role.ROLE_USER)
                .googleId(googleId)
                .provider("google")
                .enabled(true)
                .build();

        return userRepository.save(user);
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
