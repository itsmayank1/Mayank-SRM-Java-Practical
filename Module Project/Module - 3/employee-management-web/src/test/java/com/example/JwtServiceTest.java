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
        ReflectionTestUtils.setField(jwtService, "secret",
                "MyVeryLongSecretKeyWebApplicationInSpringBoot123456789");
        ReflectionTestUtils.setField(jwtService, "expiration", 3600000L);
    }

    @Test
    void testGenerateAndValidateToken() {
        String username = "hr";
        String role = "HR";

        String token = jwtService.generateToken(username, role);
        assertNotNull(token);
        assertFalse(token.isEmpty());

        assertEquals(username, jwtService.getUsername(token));
        assertEquals(role, jwtService.getRole(token));
        assertTrue(jwtService.validateToken(token, username));
        assertFalse(jwtService.validateToken(token, "otheruser"));
        assertFalse(jwtService.isTokenExpired(token));
    }
}
