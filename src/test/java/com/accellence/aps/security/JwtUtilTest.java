package com.accellence.aps.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_SECRET = "testSecretKeyWithAtLeast32CharactersForHS256AlgorithmXYZ";
    private static final long TEST_EXPIRATION = 3600000; // 1 hour

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", TEST_SECRET);
        ReflectionTestUtils.setField(jwtUtil, "jwtExpiration", TEST_EXPIRATION);
    }
    
    @Test
    void generateToken_ShouldCreateValidToken() {
        // When
        String token = jwtUtil.generateToken(TEST_USERNAME);
        
        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(TEST_USERNAME, jwtUtil.extractUsername(token));
        assertEquals("ADMIN", jwtUtil.extractRole(token));
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        // Given
        String token = jwtUtil.generateToken(TEST_USERNAME);
        
        // When
        String extractedUsername = jwtUtil.extractUsername(token);
        
        // Then
        assertEquals(TEST_USERNAME, extractedUsername);
    }
    
    @Test
    void extractExpiration_ShouldReturnValidExpirationDate() {
        // Given
        String token = jwtUtil.generateToken(TEST_USERNAME);
        
        // When
        Date expirationDate = jwtUtil.extractExpiration(token);
        
        // Then
        assertTrue(expirationDate.after(new Date()));
        long currentTime = System.currentTimeMillis();
        // Check that expiration is in the future and within expected range
        assertTrue(expirationDate.getTime() > currentTime);
        assertTrue(expirationDate.getTime() <= (currentTime + TEST_EXPIRATION + 30000)); // Increased tolerance for test stability
    }
    
    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        // Given
        String token = jwtUtil.generateToken(TEST_USERNAME);
        UserDetails userDetails = new User(TEST_USERNAME, "password", new ArrayList<>());
        
        // When
        boolean isValid = jwtUtil.validateToken(token, userDetails);
        
        // Then
        assertTrue(isValid);
    }
    
    @Test
    void validateToken_ShouldReturnFalseForInvalidUsername() {
        // Given
        String token = jwtUtil.generateToken(TEST_USERNAME);
        UserDetails userDetails = new User("wronguser", "password", new ArrayList<>());
        
        // When
        boolean isValid = jwtUtil.validateToken(token, userDetails);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    void isTokenExpired_ShouldReturnFalseForValidToken() {
        // Given
        String token = jwtUtil.generateToken(TEST_USERNAME);
        
        // When
        boolean isExpired = jwtUtil.isTokenExpired(token);
        
        // Then
        assertFalse(isExpired);
    }
    
    @Test
    void isTokenValid_ShouldReturnTrueForValidToken() {
        // Given
        String token = jwtUtil.generateToken(TEST_USERNAME);
        
        // When
        boolean isValid = jwtUtil.isTokenValid(token);
        
        // Then
        assertTrue(isValid);
    }
    
    @Test
    void extractRole_ShouldReturnCorrectRole() {
        // Given
        String token = jwtUtil.generateToken(TEST_USERNAME);
        
        // When
        String role = jwtUtil.extractRole(token);
        
        // Then
        assertEquals("ADMIN", role);
    }
}
