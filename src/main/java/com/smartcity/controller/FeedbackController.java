package com.smartcity.controller;

import com.smartcity.model.Feedback;
import com.smartcity.model.User;
import com.smartcity.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    // Web controller for feedback form - now allows public access
    @GetMapping("/feedback")
    public String feedbackPage(Model model, Authentication authentication) {
        // Allow both authenticated and non-authenticated users
        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
        return "feedback";
    }

    // Handle feedback form submission - now supports public access
    @PostMapping("/feedback")
    public String submitFeedback(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam(required = false) String phone,
                                @RequestParam String feedbackType,
                                @RequestParam String subject,
                                @RequestParam int rating,
                                @RequestParam String message,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {

        try {
            Feedback feedback = new Feedback();
            feedback.setName(name);
            feedback.setEmail(email);
            feedback.setPhone(phone);
            feedback.setSubject(subject);
            feedback.setMessage(message);
            feedback.setRating(rating);
            feedback.setFeedbackType(Feedback.FeedbackType.valueOf(feedbackType));

            // If user is authenticated, also link to user account
            if (authentication != null && authentication.isAuthenticated()) {
                User user = (User) authentication.getPrincipal();
                feedback.setUser(user);
            }

            feedbackService.save(feedback);
            redirectAttributes.addFlashAttribute("success",
                "Thank you for your feedback! Your input helps us improve our city services. We will review your feedback and get back to you if needed.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                "There was an error submitting your feedback. Please try again.");
        }

        return "redirect:/feedback";
    }

    // REST API endpoints
    @RestController
    @RequestMapping("/api/feedback")
    @RequiredArgsConstructor
    public static class FeedbackRestController {

        private final FeedbackService feedbackService;

        @PostMapping
        public Feedback submitFeedback(@RequestBody Feedback feedback, Authentication authentication) {
            if (authentication != null && authentication.isAuthenticated()) {
                User user = (User) authentication.getPrincipal();
                feedback.setUser(user);
            }
            return feedbackService.save(feedback);
        }

        @GetMapping("/service/{serviceId}")
        public List<Feedback> getFeedbackByServiceId(@PathVariable Long serviceId) {
            return feedbackService.findByServiceId(serviceId);
        }

        @GetMapping("/user/{userId}")
        public List<Feedback> getFeedbackByUserId(@PathVariable Long userId) {
            return feedbackService.findByUserId(userId);
        }
    }
}
