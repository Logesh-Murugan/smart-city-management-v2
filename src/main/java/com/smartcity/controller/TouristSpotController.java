package com.smartcity.controller;

import com.smartcity.model.TouristSpot;
import com.smartcity.service.TouristSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourist-spots")
@RequiredArgsConstructor
public class TouristSpotController {

    private final TouristSpotService touristSpotService;

    @PostMapping
    public TouristSpot addSpot(@RequestBody TouristSpot spot) {
        return touristSpotService.save(spot);
    }

    @GetMapping
    public List<TouristSpot> getAllSpots() {
        return touristSpotService.findAll();
    }
}
