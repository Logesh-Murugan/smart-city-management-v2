package com.smartcity.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "utility_services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilityService {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String serviceName;  // e.g., "Electricity Board"
    
    @Column(nullable = false)
    private String department;   // e.g., "Tamil Nadu Electricity Board (TNEB)"
    
    @Column(nullable = false)
    private String officeAddress;
    
    @Column(nullable = false)
    private String contactNumbers;  // Can store multiple numbers separated by commas
    
    private String email;  // Optional email for complaints
    
    @Column(nullable = false)
    private String workingHours;  // e.g., "Mon-Sat 9 AM–5 PM"
    
    @Column(length = 1000)
    private String description;  // Service description
    
    private String onlineRequestLink;  // Optional link for online requests
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UtilityType utilityType;
    
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
    
    // Enum for utility types
    public enum UtilityType {
        ELECTRICITY("Electricity"),
        WATER("Water Supply"),
        WASTE("Waste Management"),
        STREET_LIGHT("Street Light"),
        SEWAGE("Sewage"),
        GAS("Gas Supply"),
        INTERNET("Internet/Broadband"),
        OTHER("Other");
        
        private final String displayName;
        
        UtilityType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}
