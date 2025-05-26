package com.accellence.aps.config;

import com.accellence.aps.entity.Admin;
import com.accellence.aps.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create Admin User If It Doesn't Exist
            if (adminRepository.findByUsername("admin_test_exam").isEmpty()) {
                Admin admin = new Admin();
                admin.setUsername("admin_test_exam");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                adminRepository.save(admin);
                
                System.out.println("Default admin user created with username: admin_test_exam and password: admin123");
            }
        };
    }
}
