package com.accellence.aps.controller;

import com.accellence.aps.dto.JwtResponse;
import com.accellence.aps.dto.LoginRequest;
import com.accellence.aps.entity.Admin;
import com.accellence.aps.repository.AdminRepository;
import com.accellence.aps.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    private static final String USERNAME = "admin_test";
    private static final String PASSWORD = "password123";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    private static final String ROLE = "ADMIN";
    private static final String TOKEN = "jwtToken";
    
    private Admin admin;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        admin = new Admin();
        admin.setUsername(USERNAME);
        admin.setPassword(ENCODED_PASSWORD);
        admin.setRole(ROLE);
        
        loginRequest = new LoginRequest(USERNAME, PASSWORD);
    }

    @Test
    void login_WithValidCredentials_ShouldReturnToken() {
        // Given
        when(adminRepository.findByUsername(USERNAME)).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(true);
        when(jwtUtil.generateToken(USERNAME)).thenReturn(TOKEN);

        // When
        ResponseEntity<JwtResponse> response = authController.login(loginRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(TOKEN, response.getBody().getToken());
        assertEquals(USERNAME, response.getBody().getUsername());
        assertEquals(ROLE, response.getBody().getRole());
    }

    @Test
    void login_WithInvalidUsername_ShouldThrowBadCredentialsException() {
        // Given
        when(adminRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            authController.login(loginRequest);
        });
        
        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test
    void login_WithInvalidPassword_ShouldThrowBadCredentialsException() {
        // Given
        when(adminRepository.findByUsername(USERNAME)).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(false);

        // When & Then
        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            authController.login(loginRequest);
        });
        
        assertEquals("Invalid username or password", exception.getMessage());
    }
}
