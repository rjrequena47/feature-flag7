package com.bytes7.feature_flag7.dto;

import com.bytes7.feature_flag7.model.Feature;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record FeatureResponse(
        UUID id,
        String name,
        String description,
        Boolean enabledByDefault,
        Instant createdAt,
        Instant updatedAt,
        List<FeatureConfigResponse> configs
) {
    public static FeatureResponse fromEntity(Feature feature) {
        return new FeatureResponse(
                feature.getId(),
                feature.getName(),
                feature.getDescription(),
                feature.getEnabledByDefault(),
                feature.getCreatedAt(),
                feature.getUpdatedAt(),
                feature.getConfigs().stream()
                        .map(FeatureConfigResponse::fromEntity)
                        .collect(Collectors.toList())
        );
    }
}
