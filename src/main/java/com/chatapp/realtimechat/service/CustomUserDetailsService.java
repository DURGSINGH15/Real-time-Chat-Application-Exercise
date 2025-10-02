package com.chatapp.realtimechat.service;

import com.chatapp.realtimechat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This service class implements Spring Security's UserDetailsService interface.
 * It's responsible for loading a user's details by their username from the database.
 */
@Service // Marks this class as a Spring service bean, making it available for dependency injection.
@RequiredArgsConstructor // Lombok annotation to generate a constructor with all final fields. This is used for constructor injection.
public class CustomUserDetailsService implements UserDetailsService {

    // Inject the UserRepository to interact with the user data in the database.
    private final UserRepository userRepository;

    /**
     * This method is called by Spring Security when it needs to authenticate a user.
     * It loads the user from the database by their username.
     *
     * @param username The username of the user to load.
     * @return A UserDetails object that Spring Security can use for authentication and validation.
     * @throws UsernameNotFoundException if no user is found with the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Use the UserRepository to find a user by their username.
        // We use orElseThrow to handle the case where the user is not found.
        // If the user doesn't exist, we throw UsernameNotFoundException, which is the
        // expected behavior for this method's contract.
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}