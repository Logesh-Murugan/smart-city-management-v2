package com.smartcity.service;

import com.smartcity.model.User;
import com.smartcity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Spring Security UserDetailsService implementation
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    // User registration
    public User registerUser(String name, String username, String email, String password) {
        if (existsByUsername(username)) {
            throw new RuntimeException("Username already exists: " + username);
        }
        if (existsByEmail(email)) {
            throw new RuntimeException("Email already exists: " + email);
        }

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // Create admin user
    public User createAdmin(String name, String username, String email, String password) {
        User admin = new User();
        admin.setName(name);
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole("ROLE_ADMIN");
        admin.setActive(true);
        admin.setCreatedAt(LocalDateTime.now());

        return userRepository.save(admin);
    }

    // Existing methods
    public User save(User user) {
        // Encrypt password before saving if it's not already encrypted
        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User toggleUserStatus(Long id) {
        User user = findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(!user.isEnabled());
        return userRepository.save(user);
    }

    // Get count of users by role
    public long countByRole(String role) {
        return userRepository.findAll().stream()
                .filter(user -> role.equals(user.getRole()))
                .count();
    }

    // Get all role counts
    public java.util.Map<String, Long> getRoleCounts() {
        java.util.Map<String, Long> counts = new java.util.HashMap<>();
        counts.put("ROLE_ADMIN", countByRole("ROLE_ADMIN"));
        counts.put("ROLE_USER", countByRole("ROLE_USER"));
        return counts;
    }
}
