package com.smartcity.service;

import com.smartcity.model.EmergencyContact;
import com.smartcity.repository.EmergencyContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmergencyContactService {

    private final EmergencyContactRepository emergencyContactRepository;

    public EmergencyContact save(EmergencyContact contact) {
        return emergencyContactRepository.save(contact);
    }

    public List<EmergencyContact> findAll() {
        return emergencyContactRepository.findAll();
    }

    public Optional<EmergencyContact> findById(Long id) {
        return emergencyContactRepository.findById(id);
    }

    public void deleteById(Long id) {
        emergencyContactRepository.deleteById(id);
    }

    // Get count of contacts by service type
    public long countByServiceType(String serviceType) {
        return emergencyContactRepository.findAll().stream()
                .filter(contact -> serviceType.equals(contact.getServiceType()))
                .count();
    }

    // Get all service type counts
    public java.util.Map<String, Long> getServiceTypeCounts() {
        java.util.Map<String, Long> counts = new java.util.HashMap<>();
        List<EmergencyContact> allContacts = findAll();
        
        // Get all unique service types from existing data
        java.util.Set<String> serviceTypes = allContacts.stream()
            .map(EmergencyContact::getServiceType)
            .filter(type -> type != null && !type.trim().isEmpty())
            .collect(java.util.stream.Collectors.toSet());
        
        // Count each service type
        for (String serviceType : serviceTypes) {
            counts.put(serviceType, countByServiceType(serviceType));
        }
        
        // Add default service types if they don't exist
        if (!counts.containsKey("Police")) counts.put("Police", 0L);
        if (!counts.containsKey("Fire")) counts.put("Fire", 0L);
        if (!counts.containsKey("Medical")) counts.put("Medical", 0L);
        if (!counts.containsKey("Emergency")) counts.put("Emergency", 0L);
        
        return counts;
    }
}
