package com.example.gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JwtUtil provides utility methods for JWT (JSON Web Token) operations,
 * including token validation and claims extraction.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    /**
     * Initializes the HMAC key used for JWT signing and validation.
     * This method is called automatically after dependency injection.
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Extracts all claims from a given JWT token.
     *
     * @param token the JWT token
     * @return Claims object containing all the claims from the token
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * Checks if the given token has expired.
     *
     * @param token the JWT token
     * @return true if the token has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    /**
     * Validates the given token.
     * Currently, this method only checks if the token has expired.
     *
     * @param token the JWT token
     * @return true if the token is invalid (expired), false otherwise
     */
    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }
}