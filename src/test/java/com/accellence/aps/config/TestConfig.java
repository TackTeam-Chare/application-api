package com.accellence.aps.config;

import com.accellence.aps.entity.Admin;
import com.accellence.aps.repository.AdminRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public PasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public Admin testAdmin() {
        Admin admin = new Admin();
        admin.setUsername("admin_test");
        admin.setPassword("$2a$10$testHashedPassword");
        admin.setRole("ADMIN");
        return admin;
    }
}
