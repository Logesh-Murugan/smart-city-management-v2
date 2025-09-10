package com.smartcity.service;

import com.smartcity.model.CityService;
import com.smartcity.repository.CityServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceService {

    private final CityServiceRepository cityServiceRepository;

    public CityService save(CityService cityService) {
        return cityServiceRepository.save(cityService);
    }

    public List<CityService> findAll() {
        return cityServiceRepository.findAll();
    }

    public Optional<CityService> findById(Long id) {
        return cityServiceRepository.findById(id);
    }

    public List<CityService> findByType(String type) {
        return cityServiceRepository.findByType(type);
    }

    public List<CityService> searchByName(String name) {
        return cityServiceRepository.findByNameContaining(name);
    }

    public void deleteById(Long id) {
        cityServiceRepository.deleteById(id);
    }
}
