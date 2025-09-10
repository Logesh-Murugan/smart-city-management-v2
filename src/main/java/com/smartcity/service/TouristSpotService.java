package com.smartcity.service;

import com.smartcity.model.TouristSpot;
import com.smartcity.repository.TouristSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TouristSpotService {

    private final TouristSpotRepository touristSpotRepository;

    public TouristSpot save(TouristSpot spot) {
        return touristSpotRepository.save(spot);
    }

    public List<TouristSpot> findAll() {
        return touristSpotRepository.findAll();
    }

    public Optional<TouristSpot> findById(Long id) {
        return touristSpotRepository.findById(id);
    }

    public void deleteById(Long id) {
        touristSpotRepository.deleteById(id);
    }

    // Get count of spots by category
    public long countByCategory(String category) {
        return touristSpotRepository.findAll().stream()
                .filter(spot -> category.equals(spot.getCategory()))
                .count();
    }

    // Get all category counts
    public java.util.Map<String, Long> getCategoryCounts() {
        java.util.Map<String, Long> counts = new java.util.HashMap<>();
        List<TouristSpot> allSpots = findAll();
        
        // Add default categories
        counts.put("Religious", 0L);
        counts.put("Museum", 0L);
        counts.put("Beach", 0L);
        counts.put("Cultural", 0L);
        counts.put("Natural", 0L);
        counts.put("Shopping", 0L);
        counts.put("Historical", 0L);
        counts.put("Adventure", 0L);
        counts.put("Other", 0L);
        
        // Count spots by category
        for (TouristSpot spot : allSpots) {
            String category = spot.getCategory();
            if (category != null && !category.trim().isEmpty()) {
                counts.put(category, counts.getOrDefault(category, 0L) + 1L);
            }
        }
        
        return counts;
    }
}
