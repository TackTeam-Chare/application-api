package com.accellence.aps.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsValid_ShouldHaveNoViolations() {
        // Given
        LoginRequest loginRequest = new LoginRequest("testuser", "password123");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenUsernameIsBlank_ShouldHaveViolation() {
        // Given
        LoginRequest loginRequest = new LoginRequest("", "password123");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("Username is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenUsernameIsNull_ShouldHaveViolation() {
        // Given
        LoginRequest loginRequest = new LoginRequest(null, "password123");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("Username is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenPasswordIsBlank_ShouldHaveViolation() {
        // Given
        LoginRequest loginRequest = new LoginRequest("testuser", "");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("Password is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenPasswordIsNull_ShouldHaveViolation() {
        // Given
        LoginRequest loginRequest = new LoginRequest("testuser", null);

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("Password is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenBothFieldsAreInvalid_ShouldHaveTwoViolations() {
        // Given
        LoginRequest loginRequest = new LoginRequest("", "");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertEquals(2, violations.size());
    }

    @Test
    void testGettersAndSetters() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        
        // When
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");
        
        // Then
        assertEquals("testuser", loginRequest.getUsername());
        assertEquals("password123", loginRequest.getPassword());
    }
}
