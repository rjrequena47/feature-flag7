package com.bytes7.feature_flag7.dto;

import com.bytes7.feature_flag7.backend.enums.Environment;

public record FeatureConfigRequest(
    Environment environment,
    String clientId,
    Boolean enabled
) {}
