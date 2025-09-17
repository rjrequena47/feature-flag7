package com.bytes7.feature_flag7.service;

import com.bytes7.feature_flag7.backend.enums.Environment;
import com.bytes7.feature_flag7.dto.FeatureConfigRequest;
import com.bytes7.feature_flag7.dto.FeatureConfigResponse;
import com.bytes7.feature_flag7.dto.FeatureToggleRequest;
import com.bytes7.feature_flag7.model.Feature;
import com.bytes7.feature_flag7.model.FeatureConfig;
import com.bytes7.feature_flag7.repository.FeatureConfigRepository;
import com.bytes7.feature_flag7.repository.FeatureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class FeatureConfigService {

    private final FeatureRepository featureRepository;
    private final FeatureConfigRepository featureConfigRepository;

    public FeatureConfigService(FeatureRepository featureRepository,
                                FeatureConfigRepository featureConfigRepository) {
        this.featureRepository = featureRepository;
        this.featureConfigRepository = featureConfigRepository;
    }

    public FeatureConfigResponse enableFeature(UUID featureId, FeatureToggleRequest request) {
        Feature feature = featureRepository.findById(featureId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feature no encontrada"));

        if (request.clientId() == null && request.environment() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe especificar clientId o environment");
        }

        FeatureConfig config = featureConfigRepository
                .findByFeatureIdAndEnvironmentAndClientId(featureId, request.environment(), request.clientId())
                .orElseGet(() -> {
                    FeatureConfig newConfig = new FeatureConfig();
                    newConfig.setFeature(feature);
                    newConfig.setClientId(request.clientId());
                    newConfig.setEnvironment(request.environment());
                    return newConfig;
                });

        config.setEnabled(true);

        FeatureConfig saved = featureConfigRepository.save(config);
        return FeatureConfigResponse.fromEntity(saved);
    }

    public FeatureConfigResponse disableFeature(UUID featureId, FeatureToggleRequest request) {
        Feature feature = featureRepository.findById(featureId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feature no encontrada"));

        if (request.clientId() == null && request.environment() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe especificar clientId o environment");
        }

        FeatureConfig config = featureConfigRepository
                .findByFeatureIdAndEnvironmentAndClientId(featureId, request.environment(), request.clientId())
                .orElseGet(() -> {
                    FeatureConfig newConfig = new FeatureConfig();
                    newConfig.setFeature(feature);
                    newConfig.setClientId(request.clientId());
                    newConfig.setEnvironment(request.environment());
                    return newConfig;
                });

        config.setEnabled(false);

        FeatureConfig saved = featureConfigRepository.save(config);
        return FeatureConfigResponse.fromEntity(saved);
    }
}
