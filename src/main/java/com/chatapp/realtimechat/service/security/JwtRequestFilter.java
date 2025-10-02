package com.chatapp.realtimechat.service.security;

import com.chatapp.realtimechat.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A custom filter that intercepts every request once to handle JWT authentication.
 * It checks for a JWT in the Authorization header, validates it, and sets the
 * user's authentication in the Spring Security Context.
 */
@Component // Marks this class as a Spring component, making it eligible for dependency injection.
@RequiredArgsConstructor // Lombok annotation for constructor injection of final fields.
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    /**
     * The main filtering logic. This method is called for each incoming request.
     * @param request The incoming HTTP request.
     * @param response The outgoing HTTP response.
     * @param filterChain The chain of filters to pass the request to.
     * @throws ServletException If a servlet error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Extract the Authorization header.
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 2. Check if the header is present and follows the "Bearer " convention.
        // If not, pass the request to the next filter and exit.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the JWT from the header.
        jwt = authHeader.substring(7); // "Bearer ".length() is 7

        // 4. Extract the username from the token using our JwtUtil.
        username = jwtUtil.extractUsername(jwt);

        // 5. Check if a username was extracted and if the user is not already authenticated.
        // The second check is important to prevent re-doing the work for every filter in the chain.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 6. Load the UserDetails object for the user.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 7. Validate the token against the loaded UserDetails.
            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                // 8. If the token is valid, create an authentication token.
                // This is the object that Spring Security uses to represent the authenticated user.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credentials are not needed as the user is already authenticated by the token.
                        userDetails.getAuthorities()
                );

                // 9. Enhance the authentication token with details from the request (e.g., IP address).
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 10. Set the authentication token in the SecurityContext.
                // This is the crucial step that marks the current user as authenticated.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 11. Pass the request and response to the next filter in the chain.
        filterChain.doFilter(request, response);
    }
}