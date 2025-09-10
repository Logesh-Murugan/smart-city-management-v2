package com.smartcity.service;

import com.smartcity.model.UtilityService;
import com.smartcity.repository.UtilityServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilityServiceService {
    
    private final UtilityServiceRepository utilityServiceRepository;
    
    // Get all utility services
    public List<UtilityService> findAll() {
        return utilityServiceRepository.findAllByOrderByServiceNameAsc();
    }
    
    // Get utility service by ID
    public Optional<UtilityService> findById(Long id) {
        return utilityServiceRepository.findById(id);
    }
    
    // Get services by utility type
    public List<UtilityService> findByUtilityType(UtilityService.UtilityType utilityType) {
        return utilityServiceRepository.findByUtilityTypeOrderByServiceNameAsc(utilityType);
    }
    
    // Search services by name
    public List<UtilityService> searchByServiceName(String serviceName) {
        return utilityServiceRepository.findByServiceNameContainingIgnoreCase(serviceName);
    }
    
    // Search services by department
    public List<UtilityService> searchByDepartment(String department) {
        return utilityServiceRepository.findByDepartmentContainingIgnoreCase(department);
    }
    
    // Save utility service
    public UtilityService save(UtilityService utilityService) {
        return utilityServiceRepository.save(utilityService);
    }
    
    // Delete utility service
    public void deleteById(Long id) {
        utilityServiceRepository.deleteById(id);
    }
    
    // Check if service exists
    public boolean existsById(Long id) {
        return utilityServiceRepository.existsById(id);
    }
    
    // Get count of services by type
    public long countByType(UtilityService.UtilityType utilityType) {
        return utilityServiceRepository.findByUtilityType(utilityType).size();
    }
    
    // Get all utility types with counts
    public java.util.Map<UtilityService.UtilityType, Long> getUtilityTypeCounts() {
        java.util.Map<UtilityService.UtilityType, Long> counts = new java.util.HashMap<>();
        for (UtilityService.UtilityType type : UtilityService.UtilityType.values()) {
            counts.put(type, countByType(type));
        }
        return counts;
    }
}
