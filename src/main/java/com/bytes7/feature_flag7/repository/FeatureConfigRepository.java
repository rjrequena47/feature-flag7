package com.bytes7.feature_flag7.repository;

import com.bytes7.feature_flag7.model.FeatureConfig;
import com.bytes7.feature_flag7.backend.enums.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeatureConfigRepository extends JpaRepository<FeatureConfig, UUID> {

    List<FeatureConfig> findByFeatureId(UUID featureId);

    Optional<FeatureConfig> findByFeatureIdAndEnvironmentAndClientId(UUID featureId,
                                                                    Environment environment,
                                                                    String clientId);

    boolean existsByFeatureIdAndEnvironmentAndClientId(UUID featureId,
                                                    Environment environment,
                                                    String clientId);
}
