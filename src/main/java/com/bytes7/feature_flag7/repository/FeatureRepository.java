package com.bytes7.feature_flag7.repository;

import com.bytes7.feature_flag7.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FeatureRepository extends JpaRepository<Feature, UUID>{

    Optional<Feature> findByName(String name);

    boolean existsByName(String name);

}
