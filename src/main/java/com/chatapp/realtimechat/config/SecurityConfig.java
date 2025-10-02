package com.chatapp.realtimechat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
// Adding the new import for PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// New imports for AuthenticationManager
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

// Add new imports for JWT custom filter
import com.chatapp.realtimechat.service.security.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// Add this new import for getting list of channels
import org.springframework.http.HttpMethod;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 * Main security configuration class for the application.
 * This class defines the security filter chain that dictates which endpoints
 * are public and which are protected.
 */
@Configuration      // Marks this class as a source of bean definitions for the application context.
@EnableWebSecurity  // The key annotation to enable Spring Security's web security support.
@RequiredArgsConstructor // Adding Lombok constructor injection for our new filter
public class SecurityConfig {

    // --- INJECT THE NEW JWT FILTER ---
    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Defines the main SecurityFilterChain bean. This bean configures the application's security policies.
     * This is the modern, component-based way of configuring Spring Security, replacing the deprecated WebSecurityConfigurerAdapter.
     *
     * @param http The HttpSecurity object to be configured. Spring injects this for us.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
//    https://docs.spring.io/spring-security/reference/servlet/configuration/java.html#jc-httpsecurity
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF Protection
                // For stateless REST APIs using tokens like JWT, CSRF protection is not necessary.
                // The client is expected to send the token with each request, which itself is a form of protection.
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configure Authorization Rules
                // This is where we define which endpoints are public and which require authentication.
                .authorizeHttpRequests(auth -> auth
                        // Allow public access to authentication endpoints (register, login)
                        .requestMatchers(antMatcher("/api/auth/**")).permitAll()
                        // Allow public access to the health check endpoint
                        .requestMatchers(antMatcher("/api/health")).permitAll()
                        // Allow public access to the H2 database console for development
                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                        // --- NEW RULE ADDITION ---
                // Use a wildcard to permit GET requests to /api/channels AND its sub-paths.
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/channels/**")).permitAll()
                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                )

                // 3. Configure Session Management
                // We configure the session management to be STATELESS.
                // This tells Spring Security not to create or use any HttpSession.
                // Each request must be independently authenticated, which is perfect for JWTs.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. (Optional but Recommended for H2 Console) Configure Frame Options
                // By default, Spring Security prevents rendering pages in an <iframe> or <frame> to prevent clickjacking.
                // The H2 console runs in a frame, so we need to explicitly allow it for requests from the same origin.
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))

                // --- ADD THE JWT FILTER TO THE CHAIN ---
                // We add our custom JWT filter before the standard UsernamePasswordAuthenticationFilter.
                // This ensures that our token-based authentication is processed first.
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // Build and return the configured HttpSecurity object.
        return http.build();
    }

    // --- NEW BEAN ADDITION ---

    /**
     * Creates a PasswordEncoder bean to be used for hashing and verifying passwords.
     * We use BCrypt, which is a strong, adaptive hashing algorithm that includes salting.
     * By defining this as a bean, Spring Security will automatically pick it up and use it
     * for its authentication processes.
     *
     * @return An instance of BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // --- NEW BEAN ADDITION ---

    /**
     * Exposes the AuthenticationManager from the security configuration as a bean.
     * This allows us to inject it into our controllers for handling login requests.
     *
     * @param configuration The authentication configuration.
     * @return The AuthenticationManager bean.
     * @throws Exception if an error occurs.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}