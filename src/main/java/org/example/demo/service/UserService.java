package org.example.demo.service;

import org.example.demo.entity.User;
import org.example.demo.entity.enums.Provider;
import org.example.demo.entity.enums.Role;
import org.example.demo.exceptions.UserWithEmailAlreadyExistsException;
import org.example.demo.exceptions.UserWithUsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    public void updateProvider(String email, Provider provider) { userRepository.setNewProviderByEmail(email, provider); }

    public Optional<Provider> getProviderByEmail(String email) { return userRepository.getProviderByEmail(email); }

    public Optional<Long> getLastId() { return userRepository.findLastId(); }

    public Optional<Role> getRoleByEmail(String email) { return userRepository.getRoleByEmail(email); }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public Optional<User> getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.ofNullable(getByUsername(username));
    }

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserWithUsernameAlreadyExistsException(user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserWithEmailAlreadyExistsException(user.getEmail());
        }
        return save(user);
    }
}
