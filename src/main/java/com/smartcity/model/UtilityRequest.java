package com.smartcity.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "utility_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilityRequest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String citizenName;
    
    @Column(nullable = false)
    private String mobileNumber;
    
    private String email;  // Optional
    
    @Column(nullable = false)
    private String address;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UtilityService.UtilityType serviceType;
    
    @Column(nullable = false)
    private String complaintType;  // Short title
    
    @Column(length = 2000, nullable = false)
    private String description;  // Full details
    
    private String attachmentPath;  // Path to uploaded file (optional)
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactMethod preferredContactMethod;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utility_service_id")
    private UtilityService utilityService;  // Reference to the utility service
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;  // Reference to the user who submitted (optional for anonymous)
    
    private String adminNotes;  // Admin can add notes
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = RequestStatus.RECEIVED;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (status == RequestStatus.RESOLVED && resolvedAt == null) {
            resolvedAt = LocalDateTime.now();
        }
    }
    
    // Enum for contact methods
    public enum ContactMethod {
        PHONE("Phone"),
        EMAIL("Email"),
        BOTH("Phone & Email");
        
        private final String displayName;
        
        ContactMethod(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Enum for request status
    public enum RequestStatus {
        RECEIVED("Received"),
        IN_PROGRESS("In Progress"),
        RESOLVED("Resolved"),
        CANCELLED("Cancelled");
        
        private final String displayName;
        
        RequestStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}
