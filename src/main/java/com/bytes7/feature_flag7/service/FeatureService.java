package com.bytes7.feature_flag7.service;

import com.bytes7.feature_flag7.dto.FeatureResponse;
import com.bytes7.feature_flag7.model.Feature;
import com.bytes7.feature_flag7.repository.FeatureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public List<FeatureResponse> getFeatures(Boolean enabled, String name) {
        List<Feature> features;

        if (enabled != null && name != null) {
            features = featureRepository.findByEnabledByDefaultAndNameContainingIgnoreCase(enabled, name);
        } else if (enabled != null) {
            features = featureRepository.findByEnabledByDefault(enabled);
        } else if (name != null) {
            features = featureRepository.findByNameContainingIgnoreCase(name);
        } else {
            features = featureRepository.findAll();
        }

        return features.stream()
                .map(FeatureResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public FeatureResponse getFeatureById(UUID id) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feature not found with id: " + id));
        return FeatureResponse.fromEntity(feature);
    }
}
