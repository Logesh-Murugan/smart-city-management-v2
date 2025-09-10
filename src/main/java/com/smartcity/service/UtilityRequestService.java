package com.smartcity.service;

import com.smartcity.model.UtilityRequest;
import com.smartcity.model.UtilityService;
import com.smartcity.model.User;
import com.smartcity.repository.UtilityRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class UtilityRequestService {
    private static final Logger logger = LoggerFactory.getLogger(UtilityRequestService.class);
    
    private final UtilityRequestRepository utilityRequestRepository;
    
    public UtilityRequestService(UtilityRequestRepository utilityRequestRepository) {
        this.utilityRequestRepository = utilityRequestRepository;
    }
    
    // Get all utility requests
    public List<UtilityRequest> findAll() {
        return utilityRequestRepository.findAllByOrderByCreatedAtDesc();
    }
    
    // Get utility request by ID
    public Optional<UtilityRequest> findById(Long id) {
        return utilityRequestRepository.findById(id);
    }
    
    // Get requests by status
    public List<UtilityRequest> findByStatus(UtilityRequest.RequestStatus status) {
        return utilityRequestRepository.findByStatusOrderByCreatedAtDesc(status);
    }
    
    // Get requests by service type
    public List<UtilityRequest> findByServiceType(UtilityService.UtilityType serviceType) {
        return utilityRequestRepository.findByServiceType(serviceType);
    }
    
    // Get requests by utility service
    public List<UtilityRequest> findByUtilityService(UtilityService utilityService) {
        return utilityRequestRepository.findByUtilityService(utilityService);
    }
    
    // Get requests by user
    public List<UtilityRequest> findByUser(Long userId) {
        return utilityRequestRepository.findByUser_Id(userId);
    }
    
    // Get requests by mobile number (for anonymous users)
    public List<UtilityRequest> findByMobileNumber(String mobileNumber) {
        return utilityRequestRepository.findByMobileNumber(mobileNumber);
    }
    
    // Save utility request
    public UtilityRequest save(UtilityRequest utilityRequest) {
        return utilityRequestRepository.save(utilityRequest);
    }
    
    // Submit new request
    public UtilityRequest submitRequest(UtilityRequest request, User user, UtilityService utilityService) {
        request.setUser(user);
        request.setUtilityService(utilityService);
        request.setStatus(UtilityRequest.RequestStatus.RECEIVED);
        logger.info("Submitting UtilityRequest with complaintType: {}", request.getComplaintType());
        return save(request);
    }
    
    // Update request status
    public UtilityRequest updateStatus(Long requestId, UtilityRequest.RequestStatus status, String adminNotes) {
        Optional<UtilityRequest> requestOpt = findById(requestId);
        if (requestOpt.isPresent()) {
            UtilityRequest request = requestOpt.get();
            request.setStatus(status);
            logger.info("Updating UtilityRequest (ID {}) to status {} with complaintType: {}", requestId, status, request.getComplaintType());
            if (adminNotes != null && !adminNotes.trim().isEmpty()) {
                request.setAdminNotes(adminNotes);
            }
            return save(request);
        }
        throw new RuntimeException("Request not found with ID: " + requestId);
    }
    
    // Delete request
    public void deleteById(Long id) {
        utilityRequestRepository.deleteById(id);
    }
    
    // Get count by status
    public long countByStatus(UtilityRequest.RequestStatus status) {
        return utilityRequestRepository.countByStatus(status);
    }
    
    // Get count by service type
    public long countByServiceType(UtilityService.UtilityType serviceType) {
        return utilityRequestRepository.countByServiceType(serviceType);
    }
    
    // Get recent requests for dashboard
    public List<UtilityRequest> getRecentRequests() {
        return utilityRequestRepository.findTop10ByOrderByCreatedAtDesc();
    }
    
    // Get status counts for dashboard
    public Map<UtilityRequest.RequestStatus, Long> getStatusCounts() {
        Map<UtilityRequest.RequestStatus, Long> statusCounts = new HashMap<>();
        for (UtilityRequest.RequestStatus status : UtilityRequest.RequestStatus.values()) {
            statusCounts.put(status, countByStatus(status));
        }
        return statusCounts;
    }
}
