package com.smartcity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "smartcity.admin")
@Data
public class AdminProperties {
    
    private String username = "smartcity_admin";
    private String password = "SecurePass@2024";
    private String name = "Smart City Administrator";
    private String email = "admin@smartcity.gov";
}
