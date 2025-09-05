package com.bytes7.feature_flag7.dto;

import com.bytes7.feature_flag7.backend.enums.Environment;
import com.bytes7.feature_flag7.model.FeatureConfig;

import java.time.Instant;
import java.util.UUID;

public record FeatureConfigResponse(
        UUID id,
        Environment environment,
        String clientId,
        Boolean enabled,
        Instant createdAt,
        Instant updatedAt
) {
    public static FeatureConfigResponse fromEntity(FeatureConfig config) {
        return new FeatureConfigResponse(
                config.getId(),
                config.getEnvironment(),
                config.getClientId(),
                config.getEnabled(),
                config.getCreatedAt(),
                config.getUpdatedAt()
        );
    }
}
