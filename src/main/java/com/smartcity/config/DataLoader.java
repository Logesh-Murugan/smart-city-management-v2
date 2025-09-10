package com.smartcity.config;

import com.smartcity.model.User;
import com.smartcity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final AdminProperties adminProperties;

    @Override
    public void run(String... args) throws Exception {
        createDefaultUsers();
    }

    private void createDefaultUsers() {
        try {
            // Check if admin user already exists
            if (!userService.existsByUsername(adminProperties.getUsername())) {
                log.info("Creating default admin user...");
                userService.createAdmin(
                    adminProperties.getName(),
                    adminProperties.getUsername(),
                    adminProperties.getEmail(),
                    adminProperties.getPassword()
                );
                log.info("✅ Default admin user created successfully!");
                log.info("🔐 Admin Login - Username: {}, Password: {}",
                    adminProperties.getUsername(), adminProperties.getPassword());
            } else {
                log.info("✅ Admin user already exists");
            }

            // Check if test user already exists
            if (!userService.existsByUsername("testuser")) {
                log.info("Creating default test user...");
                userService.registerUser(
                    "Test User",
                    "testuser",
                    "testuser@example.com",
                    "test123"
                );
                log.info("✅ Default test user created successfully!");
                log.info("🔐 Test User Login - Username: testuser, Password: test123");
            } else {
                log.info("✅ Test user already exists");
            }

        } catch (Exception e) {
            log.error("❌ Error creating default users: {}", e.getMessage());
        }
    }
}
