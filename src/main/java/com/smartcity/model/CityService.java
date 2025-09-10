package com.smartcity.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "city_services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;  // Category: Hospital, Transport, Police, Utilities, etc.

    private String address;

    private String phone;

    private String email;  // Contact email

    private String timings;  // Working hours (e.g., "9:00 AM - 5:00 PM")

    @Column(length = 500)
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
