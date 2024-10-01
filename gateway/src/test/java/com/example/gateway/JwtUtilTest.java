package com.example.gateway;

import com.example.gateway.config.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private static final String SECRET = "testSecretKeyWhichShouldBeAtLeast256BitsLong";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", SECRET);
        jwtUtil.init();
    }

    @Test
    void testGetAllClaimsFromToken() {
        String token = createToken();
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        assertNotNull(claims);
        assertEquals("testUser", claims.getSubject());
    }

    @Test
    void testIsInvalid_WithValidToken() {
        String token = createToken();
        assertFalse(jwtUtil.isInvalid(token));
    }

    @Test
    void testInit_WithNullSecret() {
        JwtUtil jwtUtilWithNullSecret = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtilWithNullSecret, "secret", null);
        assertThrows(IllegalStateException.class, jwtUtilWithNullSecret::init);
    }

    private String createToken() {
        return Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }
}