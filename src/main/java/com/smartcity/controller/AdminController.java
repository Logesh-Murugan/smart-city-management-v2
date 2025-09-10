package com.smartcity.controller;

import com.smartcity.model.User;
import com.smartcity.model.UtilityService;
import com.smartcity.model.UtilityRequest;
import com.smartcity.model.TouristSpot;
import com.smartcity.model.EmergencyContact;
import com.smartcity.model.Feedback;
import com.smartcity.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;
    private final TouristSpotService touristSpotService;
    private final CityServiceService cityServiceService;
    private final EmergencyContactService emergencyContactService;
    private final FeedbackService feedbackService;
    private final UtilityServiceService utilityServiceService;
    private final UtilityRequestService utilityRequestService;

    @GetMapping
    public String adminDashboard(Model model, Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        
        // Dashboard statistics
        model.addAttribute("admin", admin);
        model.addAttribute("totalUsers", userService.findAll().size());
        model.addAttribute("totalTouristSpots", touristSpotService.findAll().size());
        model.addAttribute("totalCityServices", cityServiceService.findAll().size());
        model.addAttribute("totalEmergencyContacts", emergencyContactService.findAll().size());
        model.addAttribute("totalFeedbacks", feedbackService.findAll().size());
        model.addAttribute("totalUtilityServices", utilityServiceService.findAll().size());
        model.addAttribute("totalUtilityRequests", utilityRequestService.findAll().size());
        model.addAttribute("pendingRequests", utilityRequestService.countByStatus(UtilityRequest.RequestStatus.RECEIVED));
        model.addAttribute("inProgressRequests", utilityRequestService.countByStatus(UtilityRequest.RequestStatus.IN_PROGRESS));
        
        // Recent data for quick overview
        model.addAttribute("recentUsers", userService.findAll().stream().limit(5).toList());
        model.addAttribute("recentTouristSpots", touristSpotService.findAll().stream().limit(5).toList());
        model.addAttribute("recentCityServices", cityServiceService.findAll().stream().limit(5).toList());
        
        return "admin/dashboard";
    }

    @GetMapping("/city-services")
    public String manageCityServices(Model model, @RequestParam(required = false) String type) {
        try {
            List<com.smartcity.model.CityService> cityServices;
            if (type != null && !type.isEmpty()) {
                cityServices = cityServiceService.findByType(type);
                model.addAttribute("currentFilter", type);
            } else {
                cityServices = cityServiceService.findAll();
                model.addAttribute("currentFilter", "ALL");
            }
            model.addAttribute("cityServices", cityServices);
            
            // Add statistics
            List<com.smartcity.model.CityService> allServices = cityServiceService.findAll();
            model.addAttribute("totalServices", allServices.size());
            
            // Count services by type
            long governmentCount = allServices.stream().filter(s -> "GOVERNMENT".equals(s.getType())).count();
            long healthcareCount = allServices.stream().filter(s -> "HEALTHCARE".equals(s.getType())).count();
            long educationCount = allServices.stream().filter(s -> "EDUCATION".equals(s.getType())).count();
            
            model.addAttribute("governmentServices", governmentCount);
            model.addAttribute("healthcareServices", healthcareCount);
            model.addAttribute("educationServices", educationCount);
            
            return "admin/city-services";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading city services: " + e.getMessage());
            model.addAttribute("cityServices", java.util.Collections.emptyList());
            return "admin/city-services";
        }
    }

    @GetMapping("/city-services/add")
    public String addCityServiceForm(Model model) {
        model.addAttribute("cityService", new com.smartcity.model.CityService());
        return "admin/city-service-form";
    }

    @PostMapping("/city-services/add")
    public String addCityService(@ModelAttribute com.smartcity.model.CityService cityService,
                                RedirectAttributes redirectAttributes) {
        try {
            cityServiceService.save(cityService);
            redirectAttributes.addFlashAttribute("success", "City service added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding city service: " + e.getMessage());
        }
        return "redirect:/admin/city-services";
    }

    @GetMapping("/city-services/edit/{id}")
    public String editCityServiceForm(@PathVariable Long id, Model model) {
        com.smartcity.model.CityService cityService = cityServiceService.findById(id).orElse(null);
        if (cityService == null) {
            return "redirect:/admin/city-services";
        }
        model.addAttribute("cityService", cityService);
        return "admin/city-service-form";
    }

    @PostMapping("/city-services/edit/{id}")
    public String editCityService(@PathVariable Long id,
                                 @ModelAttribute com.smartcity.model.CityService cityService,
                                 RedirectAttributes redirectAttributes) {
        try {
            cityService.setId(id);
            cityServiceService.save(cityService);
            redirectAttributes.addFlashAttribute("success", "City service updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating city service: " + e.getMessage());
        }
        return "redirect:/admin/city-services";
    }

    @GetMapping("/city-services/delete/{id}")
    public String deleteCityService(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            cityServiceService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "City service deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting city service: " + e.getMessage());
        }
        return "redirect:/admin/city-services";
    }

    // ===== UTILITY SERVICES MANAGEMENT =====

    @GetMapping("/utilities")
    public String manageUtilities(Model model) {
        try {
            model.addAttribute("utilityServices", utilityServiceService.findAll());
            model.addAttribute("utilityTypes", UtilityService.UtilityType.values());
            model.addAttribute("utilityTypeCounts", utilityServiceService.getUtilityTypeCounts());
            return "admin/utilities";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading utility services: " + e.getMessage());
            model.addAttribute("utilityServices", java.util.Collections.emptyList());
            model.addAttribute("utilityTypes", UtilityService.UtilityType.values());
            model.addAttribute("utilityTypeCounts", new java.util.HashMap<>());
            return "admin/utilities";
        }
    }

    @GetMapping("/utilities/add")
    public String addUtilityForm(Model model) {
        model.addAttribute("utilityService", new UtilityService());
        model.addAttribute("utilityTypes", UtilityService.UtilityType.values());
        return "admin/utility-form";
    }

    @PostMapping("/utilities/add")
    public String addUtility(@ModelAttribute UtilityService utilityService,
                            RedirectAttributes redirectAttributes) {
        utilityServiceService.save(utilityService);
        redirectAttributes.addFlashAttribute("success", "Utility service added successfully!");
        return "redirect:/admin/utilities";
    }

    @GetMapping("/utilities/edit/{id}")
    public String editUtilityForm(@PathVariable Long id, Model model) {
        UtilityService utilityService = utilityServiceService.findById(id).orElse(null);
        if (utilityService == null) {
            return "redirect:/admin/utilities";
        }
        model.addAttribute("utilityService", utilityService);
        model.addAttribute("utilityTypes", UtilityService.UtilityType.values());
        return "admin/utility-form";
    }

    @PostMapping("/utilities/edit/{id}")
    public String editUtility(@PathVariable Long id,
                             @ModelAttribute UtilityService utilityService,
                             RedirectAttributes redirectAttributes) {
        utilityService.setId(id);
        utilityServiceService.save(utilityService);
        redirectAttributes.addFlashAttribute("success", "Utility service updated successfully!");
        return "redirect:/admin/utilities";
    }

    @GetMapping("/utilities/delete/{id}")
    public String deleteUtility(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        utilityServiceService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Utility service deleted successfully!");
        return "redirect:/admin/utilities";
    }

    // ===== UTILITY REQUESTS MANAGEMENT =====

    @GetMapping("/utility-requests")
    public String manageUtilityRequests(Model model, @RequestParam(required = false) String status) {
        List<UtilityRequest> requests = Collections.emptyList();
        try {
            if (status != null && !status.isEmpty()) {
                try {
                    UtilityRequest.RequestStatus requestStatus = UtilityRequest.RequestStatus.valueOf(status.toUpperCase());
                    requests = utilityRequestService.findByStatus(requestStatus);
                    model.addAttribute("selectedStatus", requestStatus);
                    logger.debug("Loaded {} requests with status {}", requests.size(), status);
                } catch (IllegalArgumentException e) {
                    requests = utilityRequestService.findAll();
                    logger.warn("Invalid status '{}', loading all requests", status);
                }
            } else {
                requests = utilityRequestService.findAll();
                logger.debug("Loading all utility requests");
            }
        } catch (Exception e) {
            logger.error("Error loading utility requests", e);
            requests = Collections.emptyList();
        }
        model.addAttribute("utilityRequests", requests);
        model.addAttribute("requestStatuses", UtilityRequest.RequestStatus.values());
        try {
            Map<UtilityRequest.RequestStatus, Long> statusCounts = utilityRequestService.getStatusCounts();
            if (statusCounts != null) {
                long totalReq = statusCounts.values().stream()
                        .filter(count -> count != null)
                        .mapToLong(Long::longValue)
                        .sum();
                model.addAttribute("statusCounts", statusCounts);
                model.addAttribute("totalRequests", totalReq);
                model.addAttribute("receivedRequests", statusCounts.getOrDefault(UtilityRequest.RequestStatus.RECEIVED, 0L));
                model.addAttribute("inProgressRequests", statusCounts.getOrDefault(UtilityRequest.RequestStatus.IN_PROGRESS, 0L));
                model.addAttribute("resolvedRequests", statusCounts.getOrDefault(UtilityRequest.RequestStatus.RESOLVED, 0L));
            } else {
                model.addAttribute("statusCounts", new HashMap<>());
                model.addAttribute("totalRequests", 0L);
                model.addAttribute("receivedRequests", 0L);
                model.addAttribute("inProgressRequests", 0L);
                model.addAttribute("resolvedRequests", 0L);
            }
        } catch (Exception e) {
            logger.error("Error getting status counts", e);
            model.addAttribute("statusCounts", new HashMap<>());
            model.addAttribute("totalRequests", 0L);
            model.addAttribute("receivedRequests", 0L);
            model.addAttribute("inProgressRequests", 0L);
            model.addAttribute("resolvedRequests", 0L);
        }
        return "admin/utility-requests";
    }

    @GetMapping("/utility-requests/{id}")
    public String viewUtilityRequest(@PathVariable Long id, Model model) {
        UtilityRequest request = utilityRequestService.findById(id).orElse(null);
        if (request == null) {
            return "redirect:/admin/utility-requests";
        }
        model.addAttribute("utilityRequest", request);
        model.addAttribute("requestStatuses", UtilityRequest.RequestStatus.values());
        return "admin/utility-request-details";
    }

    @PostMapping("/utility-requests/{id}/update-status")
    public String updateRequestStatus(@PathVariable Long id,
                                    @RequestParam UtilityRequest.RequestStatus status,
                                    @RequestParam(required = false) String adminNotes,
                                    RedirectAttributes redirectAttributes) {
        try {
            utilityRequestService.updateStatus(id, status, adminNotes);
            redirectAttributes.addFlashAttribute("success", "Request status updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update request status.");
        }
        return "redirect:/admin/utility-requests/" + id;
    }

    @GetMapping("/utility-requests/delete/{id}")
    public String deleteUtilityRequest(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            utilityRequestService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Utility request deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete utility request.");
        }
        return "redirect:/admin/utility-requests";
    }

    // ===== USER MANAGEMENT =====

    @GetMapping("/users")
    public String manageUsers(Model model) {
        try {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("roleCounts", userService.getRoleCounts());
            return "admin/users";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading users: " + e.getMessage());
            model.addAttribute("users", java.util.Collections.emptyList());
            model.addAttribute("roleCounts", new java.util.HashMap<>());
            return "admin/users";
        }
    }

    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/admin/users";
        }
        model.addAttribute("user", user);
        return "admin/user-details";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete user.");
        }
        return "redirect:/admin/users";
    }

    // ===== TOURIST SPOTS MANAGEMENT =====

    @GetMapping("/tourist-spots")
    public String manageTouristSpots(Model model) {
        try {
            System.out.println("🏛️ Loading tourist spots admin page...");
            
            List<TouristSpot> touristSpots = touristSpotService.findAll();
            System.out.println("📊 Found " + touristSpots.size() + " tourist spots");
            
            java.util.Map<String, Long> categoryCounts = touristSpotService.getCategoryCounts();
            System.out.println("📈 Category counts: " + categoryCounts);
            
            model.addAttribute("touristSpots", touristSpots);
            model.addAttribute("categoryCounts", categoryCounts);
            
            System.out.println("✅ Successfully loaded tourist spots data");
            return "admin/tourist-spots";
        } catch (Exception e) {
            System.err.println("❌ Error loading tourist spots: " + e.getMessage());
            e.printStackTrace();
            
            model.addAttribute("error", "Error loading tourist spots: " + e.getMessage());
            model.addAttribute("touristSpots", java.util.Collections.emptyList());
            model.addAttribute("categoryCounts", new java.util.HashMap<>());
            return "admin/tourist-spots";
        }
    }

    @GetMapping("/tourist-spots/add")
    public String addTouristSpotForm(Model model) {
        model.addAttribute("touristSpot", new TouristSpot());
        return "admin/tourist-spot-form";
    }

    @PostMapping("/tourist-spots/add")
    public String addTouristSpot(@ModelAttribute TouristSpot touristSpot,
                               RedirectAttributes redirectAttributes) {
        touristSpotService.save(touristSpot);
        redirectAttributes.addFlashAttribute("success", "Tourist spot added successfully!");
        return "redirect:/admin/tourist-spots";
    }

    @GetMapping("/tourist-spots/edit/{id}")
    public String editTouristSpotForm(@PathVariable Long id, Model model) {
        TouristSpot touristSpot = touristSpotService.findById(id).orElse(null);
        if (touristSpot == null) {
            return "redirect:/admin/tourist-spots";
        }
        model.addAttribute("touristSpot", touristSpot);
        return "admin/tourist-spot-form";
    }

    @PostMapping("/tourist-spots/edit/{id}")
    public String editTouristSpot(@PathVariable Long id,
                                @ModelAttribute TouristSpot touristSpot,
                                RedirectAttributes redirectAttributes) {
        touristSpot.setId(id);
        touristSpotService.save(touristSpot);
        redirectAttributes.addFlashAttribute("success", "Tourist spot updated successfully!");
        return "redirect:/admin/tourist-spots";
    }

    @GetMapping("/tourist-spots/delete/{id}")
    public String deleteTouristSpot(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        touristSpotService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Tourist spot deleted successfully!");
        return "redirect:/admin/tourist-spots";
    }

    // ===== EMERGENCY CONTACTS MANAGEMENT =====

    @GetMapping("/emergency-contacts")
    public String manageEmergencyContacts(Model model) {
        try {
            model.addAttribute("emergencyContacts", emergencyContactService.findAll());
            model.addAttribute("serviceTypeCounts", emergencyContactService.getServiceTypeCounts());
            return "admin/emergency-contacts";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading emergency contacts: " + e.getMessage());
            model.addAttribute("emergencyContacts", java.util.Collections.emptyList());
            model.addAttribute("serviceTypeCounts", new java.util.HashMap<>());
            return "admin/emergency-contacts";
        }
    }

    @GetMapping("/emergency-contacts/add")
    public String addEmergencyContactForm(Model model) {
        model.addAttribute("emergencyContact", new EmergencyContact());
        return "admin/emergency-contact-form";
    }

    @PostMapping("/emergency-contacts/add")
    public String addEmergencyContact(@ModelAttribute EmergencyContact emergencyContact,
                                   RedirectAttributes redirectAttributes) {
        emergencyContactService.save(emergencyContact);
        redirectAttributes.addFlashAttribute("success", "Emergency contact added successfully!");
        return "redirect:/admin/emergency-contacts";
    }

    @GetMapping("/emergency-contacts/edit/{id}")
    public String editEmergencyContactForm(@PathVariable Long id, Model model) {
        EmergencyContact emergencyContact = emergencyContactService.findById(id).orElse(null);
        if (emergencyContact == null) {
            return "redirect:/admin/emergency-contacts";
        }
        model.addAttribute("emergencyContact", emergencyContact);
        return "admin/emergency-contact-form";
    }

    @PostMapping("/emergency-contacts/edit/{id}")
    public String editEmergencyContact(@PathVariable Long id,
                                    @ModelAttribute EmergencyContact emergencyContact,
                                    RedirectAttributes redirectAttributes) {
        emergencyContact.setId(id);
        emergencyContactService.save(emergencyContact);
        redirectAttributes.addFlashAttribute("success", "Emergency contact updated successfully!");
        return "redirect:/admin/emergency-contacts";
    }

    @GetMapping("/emergency-contacts/delete/{id}")
    public String deleteEmergencyContact(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        emergencyContactService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Emergency contact deleted successfully!");
        return "redirect:/admin/emergency-contacts";
    }

    // ===== FEEDBACK MANAGEMENT =====

    @GetMapping("/feedback")
    public String manageFeedback(Model model,
                               @RequestParam(required = false) String filterType,
                               @RequestParam(required = false) String sortBy) {
        try {
            java.util.List<Feedback> allFeedbacks = feedbackService.findAll();

            // Apply filtering
            java.util.List<Feedback> filteredFeedbacks = allFeedbacks;
            if (filterType != null && !filterType.isEmpty() && !filterType.equals("ALL")) {
                filteredFeedbacks = allFeedbacks.stream()
                    .filter(f -> f.getFeedbackType() != null && f.getFeedbackType().name().equals(filterType))
                    .collect(java.util.stream.Collectors.toList());
            }

            // Apply sorting (support template's sort values)
            if (sortBy != null && !sortBy.isEmpty()) {
                switch (sortBy) {
                    case "NEWEST_FIRST":
                    case "DATE_DESC":
                        filteredFeedbacks.sort((f1, f2) -> {
                            if (f1.getCreatedAt() == null && f2.getCreatedAt() == null) return 0;
                            if (f1.getCreatedAt() == null) return 1; // nulls last
                            if (f2.getCreatedAt() == null) return -1;
                            return f2.getCreatedAt().compareTo(f1.getCreatedAt());
                        });
                        break;
                    case "OLDEST_FIRST":
                    case "DATE_ASC":
                        filteredFeedbacks.sort((f1, f2) -> {
                            if (f1.getCreatedAt() == null && f2.getCreatedAt() == null) return 0;
                            if (f1.getCreatedAt() == null) return 1;
                            if (f2.getCreatedAt() == null) return -1;
                            return f1.getCreatedAt().compareTo(f2.getCreatedAt());
                        });
                        break;
                    case "HIGHEST_RATING":
                    case "RATING_DESC":
                        filteredFeedbacks.sort((f1, f2) -> Integer.compare(f2.getRating(), f1.getRating()));
                        break;
                    case "LOWEST_RATING":
                    case "RATING_ASC":
                        filteredFeedbacks.sort((f1, f2) -> Integer.compare(f1.getRating(), f2.getRating()));
                        break;
                    default:
                        // Default sort by date descending
                        filteredFeedbacks.sort((f1, f2) -> f2.getCreatedAt().compareTo(f1.getCreatedAt()));
                        break;
                }
            } else {
                // Default sort by date descending
                filteredFeedbacks.sort((f1, f2) -> f2.getCreatedAt().compareTo(f1.getCreatedAt()));
            }

            // Calculate statistics
            long totalFeedbacks = allFeedbacks.size();
            long suggestions = allFeedbacks.stream().filter(f -> f.getFeedbackType() == Feedback.FeedbackType.SUGGESTION).count();
            long complaints = allFeedbacks.stream().filter(f -> f.getFeedbackType() == Feedback.FeedbackType.COMPLAINT).count();
            long compliments = allFeedbacks.stream().filter(f -> f.getFeedbackType() == Feedback.FeedbackType.COMPLIMENT).count();
            long general = allFeedbacks.stream().filter(f -> f.getFeedbackType() == Feedback.FeedbackType.GENERAL).count();

            double averageRating = allFeedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

            model.addAttribute("feedbacks", filteredFeedbacks);
            model.addAttribute("allFeedbacks", allFeedbacks);
            model.addAttribute("totalFeedbacks", totalFeedbacks);
            model.addAttribute("suggestions", suggestions);
            model.addAttribute("complaints", complaints);
            model.addAttribute("compliments", compliments);
            model.addAttribute("general", general);
            model.addAttribute("averageRating", averageRating);
            model.addAttribute("currentFilter", filterType);
            model.addAttribute("currentSort", sortBy);

            return "admin/feedback";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading feedback: " + e.getMessage());
            // capture full stacktrace for debugging
            java.io.StringWriter sw = new java.io.StringWriter();
            java.io.PrintWriter pw = new java.io.PrintWriter(sw);
            e.printStackTrace(pw);
            model.addAttribute("errorStack", sw.toString());
            model.addAttribute("feedbacks", java.util.Collections.emptyList());
            return "admin/feedback";
        }
    }

    @GetMapping("/feedback/{id}")
    public String viewFeedback(@PathVariable Long id, Model model) {
        Feedback feedback = feedbackService.findById(id).orElse(null);
        if (feedback == null) {
            return "redirect:/admin/feedback";
        }
        model.addAttribute("feedback", feedback);
        return "admin/feedback-details";
    }

    @GetMapping("/feedback/delete/{id}")
    public String deleteFeedback(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        feedbackService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Feedback deleted successfully!");
        return "redirect:/admin/feedback";
    }
}
