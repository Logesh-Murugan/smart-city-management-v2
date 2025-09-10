package com.smartcity.repository;

import com.smartcity.model.UtilityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UtilityServiceRepository extends JpaRepository<UtilityService, Long> {
    
    // Find services by utility type
    List<UtilityService> findByUtilityType(UtilityService.UtilityType utilityType);
    
    // Find services by service name (case-insensitive)
    List<UtilityService> findByServiceNameContainingIgnoreCase(String serviceName);
    
    // Find services by department (case-insensitive)
    List<UtilityService> findByDepartmentContainingIgnoreCase(String department);
    
    // Find all services ordered by service name
    List<UtilityService> findAllByOrderByServiceNameAsc();
    
    // Find services by utility type ordered by service name
    List<UtilityService> findByUtilityTypeOrderByServiceNameAsc(UtilityService.UtilityType utilityType);
}
