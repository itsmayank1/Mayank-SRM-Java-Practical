package com.example;

import com.example.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Set test secret key and expiration using ReflectionTestUtils
        ReflectionTestUtils.setField(jwtService, "secretKey",
                "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 3600000L); // 1 hour
    }

    @Test
    void testGenerateAndValidateToken() {
        String username = "admin";
        String role = "ROLE_ADMIN";

        String token = jwtService.generateToken(username, role);
        assertNotNull(token);
        assertFalse(token.isEmpty());

        assertEquals(username, jwtService.extractUsername(token));
        assertEquals(role, jwtService.extractRole(token));
        assertTrue(jwtService.validateToken(token, username));
        assertFalse(jwtService.validateToken(token, "otheruser"));
        assertFalse(jwtService.isTokenExpired(token));
    }
}
