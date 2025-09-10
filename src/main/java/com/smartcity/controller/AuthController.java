package com.smartcity.controller;

import com.smartcity.model.User;
import com.smartcity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        
        // If user is already authenticated, redirect to home
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            return "redirect:/";
        }
        
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully!");
        }
        
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        
        // If user is already authenticated, redirect to home
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            return "redirect:/";
        }
        
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                              @RequestParam String username,
                              @RequestParam String email,
                              @RequestParam String password,
                              @RequestParam String confirmPassword,
                              RedirectAttributes redirectAttributes) {
        
        try {
            // Validate input
            if (name == null || name.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Name is required!");
                return "redirect:/register";
            }
            
            if (username == null || username.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Username is required!");
                return "redirect:/register";
            }
            
            if (email == null || email.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Email is required!");
                return "redirect:/register";
            }
            
            if (password == null || password.length() < 6) {
                redirectAttributes.addFlashAttribute("error", "Password must be at least 6 characters long!");
                return "redirect:/register";
            }
            
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Passwords do not match!");
                return "redirect:/register";
            }
            
            // Register the user
            userService.registerUser(name.trim(), username.trim(), email.trim(), password);
            
            redirectAttributes.addFlashAttribute("success", 
                "Registration successful! You can now log in with your credentials.");
            return "redirect:/login";
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "auth/profile";
    }

    // Admin user creation endpoint (for initial setup) - accessible without authentication
    @GetMapping("/create-admin")
    public String createAdminPage() {
        return "auth/create-admin";
    }

    @PostMapping("/create-admin")
    public String createAdmin(@RequestParam String name,
                             @RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             RedirectAttributes redirectAttributes) {

        try {
            User admin = userService.createAdmin(name, username, email, password);
            System.out.println("Created admin user: " + admin.getUsername() + " with role: " + admin.getRole());
            redirectAttributes.addFlashAttribute("success",
                "Admin user created successfully! You can now login with these credentials.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/create-admin";
        }
    }

    // Debug endpoint to check current user authorities
    @GetMapping("/debug-user")
    @ResponseBody
    public String debugUser(Authentication authentication) {
        if (authentication == null) {
            return "No authentication found";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Username: ").append(authentication.getName()).append("\n");
        sb.append("Authorities: ");
        authentication.getAuthorities().forEach(auth ->
            sb.append(auth.getAuthority()).append(" "));
        sb.append("\n");
        sb.append("Principal: ").append(authentication.getPrincipal().getClass().getSimpleName());

        return sb.toString();
    }
}
