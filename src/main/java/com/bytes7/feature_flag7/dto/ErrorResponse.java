package com.bytes7.feature_flag7.dto;

import java.time.Instant;

public record ErrorResponse(
        int status,
        String message,
        Instant timestamp,
        String path
) {}
