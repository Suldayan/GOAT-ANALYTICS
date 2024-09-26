package com.example.gateway;

import com.example.gateway.config.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String SECRET = "testSecret";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", SECRET);
        jwtUtil.init();
    }

    @Test
    void testGetAllClaimsFromToken() {
        String token = Jwts.builder()
                .setSubject("user")
                .claim("role", "ADMIN")
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();

        Claims claims = jwtUtil.getAllClaimsFromToken(token);

        assertEquals("user", claims.getSubject());
        assertEquals("ADMIN", claims.get("role"));
    }

    @Test
    void testIsInvalid() {
        String validToken = Jwts.builder()
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();

        String expiredToken = Jwts.builder()
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();

        assertFalse(jwtUtil.isInvalid(validToken));
        assertTrue(jwtUtil.isInvalid(expiredToken));
    }
}