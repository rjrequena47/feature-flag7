package com.bytes7.feature_flag7.repository;

import com.bytes7.feature_flag7.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface FeatureRepository extends JpaRepository<Feature, UUID>{

    Optional<Feature> findByName(String name);

    boolean existsByName(String name);

    // Métodos para los filtros
    
    List<Feature> findByEnabledByDefault(Boolean enabled);

    List<Feature> findByNameContainingIgnoreCase(String name);

    List<Feature> findByEnabledByDefaultAndNameContainingIgnoreCase(Boolean enabled, String name);

}
