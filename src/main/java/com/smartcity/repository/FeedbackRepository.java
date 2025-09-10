package com.smartcity.repository;

import com.smartcity.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByServiceId(Long serviceId);
    List<Feedback> findByUserId(Long userId);
}
