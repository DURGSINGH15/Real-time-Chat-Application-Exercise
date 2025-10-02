package com.chatapp.realtimechat.controller;

import com.chatapp.realtimechat.dto.LoginRequest;
import com.chatapp.realtimechat.dto.RegisterRequest;
import com.chatapp.realtimechat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user authentication (registration and login).
 */
@RestController
@RequestMapping("/api/auth") // Base path for all endpoints in this controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    /**
     * Endpoint for user registration.
     * @param registerRequest DTO containing username and password.
     * @return A response entity indicating success or failure.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        // Delegate the registration logic to the UserService
        userService.registerUser(registerRequest);
        // Return a 201 Created status with a success message.
        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }

    /**
     * Endpoint for user login.
     * @param loginRequest DTO containing username and password.
     * @return A response entity with a success message or an error.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // 1. Create an authentication object with the credentials from the request.
        //    This is the standard token Spring Security uses for username/password authentication.
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        // 2. Use the AuthenticationManager to authenticate the user.
        //    This will trigger our CustomUserDetailsService and PasswordEncoder.
        //    If authentication fails, it throws an AuthenticationException, which Spring's default
        //    exception handlers will catch and convert to a 401 Unauthorized response.
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);

        // 3. If authentication is successful, store the authenticated principal in the SecurityContext.
        //    While not strictly necessary for stateless JWT auth, it's good practice.
        SecurityContextHolder.getContext().setAuthentication(authenticated);

        // NOTE: In the next step, we will generate and return a JWT here.
        // For now, we just return a success message.
        return ResponseEntity.ok("Login successful!");
    }
}