package com.smartcity.controller;

import com.smartcity.model.CityService;
import com.smartcity.service.CityServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/city-services")
@RequiredArgsConstructor
public class CityServiceController {

    private final CityServiceService cityServiceService;

    @PostMapping
    public CityService createService(@RequestBody CityService cityService) {
        return cityServiceService.save(cityService);
    }

    @GetMapping
    public List<CityService> getAllServices() {
        return cityServiceService.findAll();
    }

    @GetMapping("/type/{type}")
    public List<CityService> getByType(@PathVariable String type) {
        return cityServiceService.findByType(type);
    }

    @GetMapping("/search")
    public List<CityService> searchByName(@RequestParam String name) {
        return cityServiceService.searchByName(name);
    }
}
