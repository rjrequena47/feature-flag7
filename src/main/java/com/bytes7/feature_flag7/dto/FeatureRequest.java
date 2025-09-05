package com.bytes7.feature_flag7.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record FeatureRequest(
    String name,
    @NotBlank(message = "La descripción no puede estar vacía")
    String description,
    Boolean enabledByDefault,
    List<FeatureConfigRequest> configs
) {}
