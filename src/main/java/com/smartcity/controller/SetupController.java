package com.smartcity.controller;

import com.smartcity.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setup")
@RequiredArgsConstructor
public class SetupController {

    private final UserService userService;
    private final UtilityServiceService utilityServiceService;
    private final TouristSpotService touristSpotService;
    private final EmergencyContactService emergencyContactService;
    private final FeedbackService feedbackService;

    @GetMapping("/create-admin")
    public ResponseEntity<String> createAdmin() {
        try {
            String adminUsername = "smartcity_admin";  // 🔧 CHANGE THIS TO YOUR PREFERRED USERNAME
            String adminPassword = "SecurePass@2024";   // 🔧 CHANGE THIS TO YOUR PREFERRED PASSWORD

            if (!userService.existsByUsername(adminUsername)) {
                userService.createAdmin(
                    "Smart City Administrator",
                    adminUsername,
                    "admin@smartcity.gov",
                    adminPassword
                );
                return ResponseEntity.ok("✅ Admin user created successfully!\n" +
                    "Username: " + adminUsername + "\n" +
                    "Password: " + adminPassword);
            } else {
                return ResponseEntity.ok("✅ Admin user already exists!\n" +
                    "Username: " + adminUsername + "\n" +
                    "Password: " + adminPassword);
            }
        } catch (Exception e) {
            return ResponseEntity.ok("❌ Error: " + e.getMessage());
        }
    }

    @GetMapping("/create-testuser")
    public ResponseEntity<String> createTestUser() {
        try {
            if (!userService.existsByUsername("testuser")) {
                userService.registerUser(
                    "Test User",
                    "testuser",
                    "testuser@example.com",
                    "test123"
                );
                return ResponseEntity.ok("✅ Test user created successfully!\n" +
                    "Username: testuser\n" +
                    "Password: test123");
            } else {
                return ResponseEntity.ok("✅ Test user already exists!\n" +
                    "Username: testuser\n" +
                    "Password: test123");
            }
        } catch (Exception e) {
            return ResponseEntity.ok("❌ Error: " + e.getMessage());
        }
    }

    @GetMapping("/debug")
    public ResponseEntity<String> debugInfo() {
        try {
            StringBuilder info = new StringBuilder();
            info.append("🔍 DEBUG INFORMATION:\n\n");

            // Check users
            info.append("👥 Users in database: ").append(userService.findAll().size()).append("\n");

            // Check utility services
            info.append("⚡ Utility services: ").append(utilityServiceService.findAll().size()).append("\n");

            // Check tourist spots
            info.append("🏛️ Tourist spots: ").append(touristSpotService.findAll().size()).append("\n");

            // Check emergency contacts
            info.append("🚨 Emergency contacts: ").append(emergencyContactService.findAll().size()).append("\n");

            // Check feedback
            info.append("💬 Feedback entries: ").append(feedbackService.findAll().size()).append("\n");

            return ResponseEntity.ok(info.toString());
        } catch (Exception e) {
            return ResponseEntity.ok("❌ Debug Error: " + e.getMessage() + "\n" +
                "Stack trace: " + java.util.Arrays.toString(e.getStackTrace()));
        }
    }
}
