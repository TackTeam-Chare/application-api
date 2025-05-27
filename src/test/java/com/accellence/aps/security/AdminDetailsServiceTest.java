package com.accellence.aps.security;

import com.accellence.aps.entity.Admin;
import com.accellence.aps.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminDetailsServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminDetailsService adminDetailsService;

    private static final String USERNAME = "admin_test";
    private static final String PASSWORD = "password123";
    private static final String ROLE = "ADMIN";
    
    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin();
        admin.setUsername(USERNAME);
        admin.setPassword(PASSWORD);
        admin.setRole(ROLE);
    }

    @Test
    void loadUserByUsername_WithExistingUsername_ShouldReturnUserDetails() {
        // Given
        when(adminRepository.findByUsername(USERNAME)).thenReturn(Optional.of(admin));

        // When
        UserDetails userDetails = adminDetailsService.loadUserByUsername(USERNAME);

        // Then
        assertNotNull(userDetails);
        assertEquals(USERNAME, userDetails.getUsername());
        assertEquals(PASSWORD, userDetails.getPassword());
        
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + ROLE)));
    }

    @Test
    void loadUserByUsername_WithNonExistingUsername_ShouldThrowException() {
        // Given
        String nonExistingUsername = "nonexistent";
        when(adminRepository.findByUsername(nonExistingUsername)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            adminDetailsService.loadUserByUsername(nonExistingUsername);
        });
        
        assertEquals("Admin not found with username: " + nonExistingUsername, exception.getMessage());
    }
}
