package com.bytes7.feature_flag7.service;

import com.bytes7.feature_flag7.model.Feature;
import com.bytes7.feature_flag7.model.FeatureConfig;
import com.bytes7.feature_flag7.repository.FeatureRepository;
import com.bytes7.feature_flag7.repository.FeatureConfigRepository;
import com.bytes7.feature_flag7.backend.enums.Environment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureCheckService {

    private final FeatureRepository featureRepository;
    private final FeatureConfigRepository featureConfigRepository;

    public FeatureCheckService(FeatureRepository featureRepository,
                               FeatureConfigRepository featureConfigRepository) {
        this.featureRepository = featureRepository;
        this.featureConfigRepository = featureConfigRepository;
    }

    public boolean isFeatureActive(String featureName, String clientId, Environment env) {
        // 1. Buscar feature
        Feature feature = featureRepository.findByName(featureName)
                .orElseThrow(() -> new RuntimeException("Feature not found: " + featureName));

        // 2. Configuración específica del cliente
        Optional<FeatureConfig> clientConfig = featureConfigRepository
                .findByFeatureIdAndEnvironmentAndClientId(feature.getId(), env, clientId);

        if (clientConfig.isPresent()) {
            return clientConfig.get().getEnabled();
        }

        // 3. Configuración por entorno (sin clientId)
        Optional<FeatureConfig> envConfig = featureConfigRepository
                .findByFeatureIdAndEnvironmentAndClientId(feature.getId(), env, null);

        if (envConfig.isPresent()) {
            return envConfig.get().getEnabled();
        }

        // 4. Valor por defecto
        return feature.getEnabledByDefault();
    }
}
