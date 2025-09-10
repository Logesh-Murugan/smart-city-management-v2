package com.smartcity.repository;

import com.smartcity.model.UtilityRequest;
import com.smartcity.model.UtilityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UtilityRequestRepository extends JpaRepository<UtilityRequest, Long> {
    
    // Find requests by status
    List<UtilityRequest> findByStatus(UtilityRequest.RequestStatus status);
    
    // Find requests by service type
    List<UtilityRequest> findByServiceType(UtilityService.UtilityType serviceType);
    
    // Find requests by utility service
    List<UtilityRequest> findByUtilityService(UtilityService utilityService);
    
    // Find requests by user
    List<UtilityRequest> findByUser_Id(Long userId);
    
    // Find requests by mobile number (for anonymous users)
    List<UtilityRequest> findByMobileNumber(String mobileNumber);
    
    // Find all requests ordered by creation date (newest first)
    List<UtilityRequest> findAllByOrderByCreatedAtDesc();
    
    // Find requests by status ordered by creation date
    List<UtilityRequest> findByStatusOrderByCreatedAtDesc(UtilityRequest.RequestStatus status);
    
    // Count requests by status
    long countByStatus(UtilityRequest.RequestStatus status);
    
    // Count requests by service type
    long countByServiceType(UtilityService.UtilityType serviceType);
    
    // Find recent requests (for admin dashboard)
    @Query("SELECT ur FROM UtilityRequest ur ORDER BY ur.createdAt DESC")
    List<UtilityRequest> findTop10ByOrderByCreatedAtDesc();
}
