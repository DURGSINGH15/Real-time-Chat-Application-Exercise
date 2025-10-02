package com.chatapp.realtimechat.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A utility service for handling JSON Web Tokens (JWTs).
 * This service is responsible for generating, parsing, and validating tokens.
 */
@Service
public class JwtUtil {

    // Injecting the secret and expiration from application.properties
    private final String secret;
    private final long expirationMs;

    // The secret key used for signing and verifying tokens.
    // This is derived from the base64-encoded secret string.
    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration.ms}") long expirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
        // Decode the Base64 secret string to get the byte array
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        // Create a SecretKey instance for HMAC-SHA algorithms
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // --- Token Generation ---

    /**
     * Generates a JWT for a given user.
     * @param userDetails The user details object from Spring Security.
     * @return A signed JWT string.
     */
    public String generateToken(UserDetails userDetails) {
        // We can add extra claims (data) to the token if needed.
        Map<String, Object> extraClaims = new HashMap<>();
        // In the future, you could add roles or other user-specific data here.
        // extraClaims.put("authorities", userDetails.getAuthorities());

        return Jwts.builder()
                .claims(extraClaims) // Set the custom claims
                .subject(userDetails.getUsername()) // Set the 'subject' of the token (who it's for)
                .issuedAt(new Date(System.currentTimeMillis())) // Set the token issuance time
                .expiration(new Date(System.currentTimeMillis() + expirationMs)) // Set the expiration time
                .signWith(secretKey) // Sign the token with our secret key using the default algorithm (HS256)
                .compact(); // Build the token and serialize it to a compact, URL-safe string
    }

    // --- Token Validation and Parsing ---

    /**
     * Extracts the username (subject) from a given token.
     * @param token The JWT string.
     * @return The username.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Checks if a token is valid for a given user.
     * It verifies that the username in the token matches the user and that the token is not expired.
     * @param token The JWT string.
     * @param userDetails The user details to validate against.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * A generic function to extract a specific claim from the token.
     * @param token The JWT string.
     * @param claimsResolver A function to extract the desired claim from the Claims object.
     * @param <T> The type of the claim.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses the JWT token to extract all its claims (the payload).
     * It uses the secret key to verify the token's signature during parsing.
     * If the signature is invalid or the token is malformed, an exception will be thrown.
     * @param token The JWT string.
     * @return The Claims object containing the token's payload.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey) // Provide the secret key to verify the signature
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
