package com.smartcity.repository;

import com.smartcity.model.CityService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CityServiceRepository extends JpaRepository<CityService, Long> {
    List<CityService> findByType(String type);
    List<CityService> findByNameContaining(String name);
}
