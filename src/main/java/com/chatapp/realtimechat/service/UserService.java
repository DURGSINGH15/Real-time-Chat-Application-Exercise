package com.chatapp.realtimechat.service;

import com.chatapp.realtimechat.dto.RegisterRequest;
import com.chatapp.realtimechat.entity.User;
import com.chatapp.realtimechat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Handles the business logic for registering a new user.
     * @param request The DTO containing the new user's credentials.
     */
    public void registerUser(RegisterRequest request) {
        // Check if the username is already taken. If so, throw a client error.
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        // Build a new User entity from the request data.
        User user = User.builder()
                .username(request.getUsername())
                // IMPORTANT: Always encode the password before saving!
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();

        // Save the new user to the database.
        userRepository.save(user);
    }
}