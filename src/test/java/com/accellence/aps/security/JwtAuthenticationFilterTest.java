package com.accellence.aps.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_TOKEN = "valid.test.token";

    @BeforeEach
    void setUp() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        SecurityContextHolder.clearContext(); // Ensure security context is clean for each test
    }

    @Test
    void doFilterInternal_WithValidToken_ShouldAuthenticate() throws ServletException, IOException {
        // Given
        UserDetails userDetails = new User(TEST_USERNAME, "password", 
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        
        when(request.getHeader("Authorization")).thenReturn("Bearer " + TEST_TOKEN);
        when(jwtUtil.isTokenValid(TEST_TOKEN)).thenReturn(true);
        when(jwtUtil.extractUsername(TEST_TOKEN)).thenReturn(TEST_USERNAME);
        when(jwtUtil.extractRole(TEST_TOKEN)).thenReturn("ADMIN");
        when(userDetailsService.loadUserByUsername(TEST_USERNAME)).thenReturn(userDetails);
        when(jwtUtil.validateToken(TEST_TOKEN, userDetails)).thenReturn(true);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verify(userDetailsService).loadUserByUsername(TEST_USERNAME);
    }

    @Test
    void doFilterInternal_WithInvalidToken_ShouldNotAuthenticate() throws ServletException, IOException {
        // Given
        when(request.getHeader("Authorization")).thenReturn("Bearer " + TEST_TOKEN);
        when(jwtUtil.isTokenValid(TEST_TOKEN)).thenReturn(false);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_WithNoToken_ShouldNotAuthenticate() throws ServletException, IOException {
        // Given
        when(request.getHeader("Authorization")).thenReturn(null);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_WithInvalidTokenFormat_ShouldNotAuthenticate() throws ServletException, IOException {
        // Given
        when(request.getHeader("Authorization")).thenReturn("InvalidFormat");

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_WithValidTokenButInvalidUsername_ShouldNotAuthenticate() throws ServletException, IOException {
        // Given
        when(request.getHeader("Authorization")).thenReturn("Bearer " + TEST_TOKEN);
        when(jwtUtil.isTokenValid(TEST_TOKEN)).thenReturn(true);
        when(jwtUtil.extractUsername(TEST_TOKEN)).thenReturn(TEST_USERNAME);
        when(jwtUtil.extractRole(TEST_TOKEN)).thenReturn("ADMIN");
        when(userDetailsService.loadUserByUsername(TEST_USERNAME)).thenReturn(null);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain).doFilter(request, response);
        verify(userDetailsService).loadUserByUsername(TEST_USERNAME);
    }
}
