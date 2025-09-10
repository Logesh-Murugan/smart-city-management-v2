package com.smartcity.controller;

import com.smartcity.model.UtilityRequest;
import com.smartcity.model.UtilityService;
import com.smartcity.model.User;
import com.smartcity.service.UtilityRequestService;
import com.smartcity.service.UtilityServiceService;
import com.smartcity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/utilities")
@RequiredArgsConstructor
public class UtilityController {
    
    private final UtilityServiceService utilityServiceService;
    private final UtilityRequestService utilityRequestService;
    private final UserService userService;
    
    // Show all utility services
    @GetMapping
    public String utilityServices(Model model, 
                                 @RequestParam(required = false) String type) {
        if (type != null && !type.isEmpty()) {
            try {
                UtilityService.UtilityType utilityType = UtilityService.UtilityType.valueOf(type.toUpperCase());
                model.addAttribute("utilityServices", utilityServiceService.findByUtilityType(utilityType));
                model.addAttribute("selectedType", utilityType);
            } catch (IllegalArgumentException e) {
                model.addAttribute("utilityServices", utilityServiceService.findAll());
            }
        } else {
            model.addAttribute("utilityServices", utilityServiceService.findAll());
        }
        
        model.addAttribute("utilityTypes", UtilityService.UtilityType.values());
        model.addAttribute("typeCounts", utilityServiceService.getUtilityTypeCounts());
        return "utilities/index";
    }
    
    // Show utility service details
    @GetMapping("/{id}")
    public String utilityServiceDetails(@PathVariable Long id, Model model) {
        Optional<UtilityService> serviceOpt = utilityServiceService.findById(id);
        if (serviceOpt.isPresent()) {
            model.addAttribute("utilityService", serviceOpt.get());
            return "utilities/details";
        }
        return "redirect:/utilities";
    }
    
    // Show complaint/request form
    @GetMapping("/{id}/request")
    public String requestForm(@PathVariable Long id, Model model, Authentication authentication) {
        Optional<UtilityService> serviceOpt = utilityServiceService.findById(id);
        if (serviceOpt.isPresent()) {
            model.addAttribute("utilityService", serviceOpt.get());
            model.addAttribute("utilityRequest", new UtilityRequest());
            model.addAttribute("contactMethods", UtilityRequest.ContactMethod.values());
            
            // Pre-fill user info if logged in
            if (authentication != null) {
                Optional<User> userOpt = userService.findByUsername(authentication.getName());
                if (userOpt.isPresent()) {
                    model.addAttribute("currentUser", userOpt.get());
                }
            }
            
            return "utilities/request-form";
        }
        return "redirect:/utilities";
    }
    
    // Submit complaint/request
    @PostMapping("/{id}/request")
    public String submitRequest(@PathVariable Long id,
                               @ModelAttribute UtilityRequest utilityRequest,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        try {
            Optional<UtilityService> serviceOpt = utilityServiceService.findById(id);
            if (serviceOpt.isPresent()) {
                UtilityService service = serviceOpt.get();
                utilityRequest.setServiceType(service.getUtilityType());
                
                // Get current user if logged in
                User user = null;
                if (authentication != null) {
                    Optional<User> userOpt = userService.findByUsername(authentication.getName());
                    if (userOpt.isPresent()) {
                        user = userOpt.get();
                    }
                }
                
                utilityRequestService.submitRequest(utilityRequest, user, service);
                
                redirectAttributes.addFlashAttribute("success", 
                    "Your request has been submitted successfully! Reference ID: #" + utilityRequest.getId());
                return "redirect:/utilities/" + id;
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to submit request. Please try again.");
        }
        
        return "redirect:/utilities/" + id + "/request";
    }
    
    // Track request status
    @GetMapping("/track")
    public String trackRequest(Model model) {
        return "utilities/track";
    }
    
    // Track request by mobile number or ID
    @PostMapping("/track")
    public String trackRequestSubmit(@RequestParam(required = false) String mobileNumber,
                                   @RequestParam(required = false) Long requestId,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        try {
            if (requestId != null) {
                Optional<UtilityRequest> requestOpt = utilityRequestService.findById(requestId);
                if (requestOpt.isPresent()) {
                    model.addAttribute("requests", java.util.Arrays.asList(requestOpt.get()));
                } else {
                    redirectAttributes.addFlashAttribute("error", "Request not found with ID: " + requestId);
                    return "redirect:/utilities/track";
                }
            } else if (mobileNumber != null && !mobileNumber.trim().isEmpty()) {
                var requests = utilityRequestService.findByMobileNumber(mobileNumber.trim());
                if (requests.isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "No requests found for mobile number: " + mobileNumber);
                    return "redirect:/utilities/track";
                }
                model.addAttribute("requests", requests);
            } else {
                redirectAttributes.addFlashAttribute("error", "Please provide either Request ID or Mobile Number");
                return "redirect:/utilities/track";
            }
            
            return "utilities/track-results";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error tracking request. Please try again.");
            return "redirect:/utilities/track";
        }
    }
}
