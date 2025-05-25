package com.accellence.aps.controller;

import com.accellence.aps.dto.JwtResponse;
import com.accellence.aps.dto.LoginRequest;
import com.accellence.aps.entity.Admin;
import com.accellence.aps.repository.AdminRepository;
import com.accellence.aps.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthController(AdminRepository adminRepository, 
                         PasswordEncoder passwordEncoder, 
                         JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Find username
        Admin admin = adminRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
        
        // Check if Password Matches
        if (!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        
        // Generate token
        String token = jwtUtil.generateToken(admin.getUsername());
        
        // Return token
        return ResponseEntity.ok(new JwtResponse(token, admin.getUsername(), admin.getRole()));
    }
}
