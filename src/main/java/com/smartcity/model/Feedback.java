package com.smartcity.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Many feedbacks can belong to one User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 🔗 Many feedbacks can belong to one CityService
    @ManyToOne
    @JoinColumn(name = "service_id")
    private CityService service;

    private String name;

    private String email;

    private String phone;

    private String subject;

    @Column(length = 2000)
    private String message;

    private int rating;

    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

    private LocalDateTime createdAt;

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

    public enum FeedbackType {
        SUGGESTION("Suggestion"),
        COMPLAINT("Complaint"),
        COMPLIMENT("Compliment"),
        GENERAL("General Feedback");

        private final String displayName;

        FeedbackType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
