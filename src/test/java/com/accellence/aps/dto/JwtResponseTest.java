package com.accellence.aps.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtResponseTest {

    private static final String TOKEN = "jwtToken";
    private static final String USERNAME = "admin_test";
    private static final String ROLE = "ADMIN";

    @Test
    void whenCreated_ShouldReturnCorrectValues() {
        // Given
        JwtResponse jwtResponse = new JwtResponse(TOKEN, USERNAME, ROLE);
        
        // Then
        assertEquals(TOKEN, jwtResponse.getToken());
        assertEquals(USERNAME, jwtResponse.getUsername());
        assertEquals(ROLE, jwtResponse.getRole());
    }
    
    @Test
    void testSetters() {
        // Given
        JwtResponse jwtResponse = new JwtResponse("initialToken", "initialUsername", "initialRole");
        
        // When
        jwtResponse.setToken(TOKEN);
        jwtResponse.setUsername(USERNAME);
        jwtResponse.setRole(ROLE);
        
        // Then
        assertEquals(TOKEN, jwtResponse.getToken());
        assertEquals(USERNAME, jwtResponse.getUsername());
        assertEquals(ROLE, jwtResponse.getRole());
    }
}
