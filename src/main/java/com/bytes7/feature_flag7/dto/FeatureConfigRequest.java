package com.bytes7.feature_flag7.dto;

import com.bytes7.feature_flag7.backend.enums.Environment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FeatureConfigRequest(
    @NotNull(message = "El entorno es obligatorio")    
    Environment environment,
    @Size(max = 50, message = "El clientId no debe superar los 50 caracteres")
    String clientId,
    @NotNull(message = "Debe indicar si la feature está habilitada")
    Boolean enabled
) {}
