package com.smartcity.service;

import com.smartcity.model.Feedback;
import com.smartcity.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public Feedback save(Feedback feedback) {
        // The timestamps are now handled by @PrePersist and @PreUpdate
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> findByServiceId(Long serviceId) {
        return feedbackRepository.findByServiceId(serviceId);
    }

    public List<Feedback> findByUserId(Long userId) {
        return feedbackRepository.findByUserId(userId);
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> findById(Long id) {
        return feedbackRepository.findById(id);
    }

    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }
}
