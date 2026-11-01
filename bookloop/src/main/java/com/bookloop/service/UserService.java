package com.bookloop.service;

import com.bookloop.dto.RegisterDto;
import com.bookloop.entity.User;
import com.bookloop.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * UserService
 * Handles user registration, password hashing (BCrypt), and user lookups.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email.trim().toLowerCase());
    }

    @Transactional
    public User registerUser(RegisterDto dto) {
        // Normalize email to lowercase
        String normalizedEmail = dto.getEmail().trim().toLowerCase();

        // Hash password securely with BCrypt before saving
        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        User user = new User(
                dto.getName().trim(),
                normalizedEmail,
                hashedPassword,
                dto.getCollegeName().trim()
        );

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email.trim().toLowerCase());
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
