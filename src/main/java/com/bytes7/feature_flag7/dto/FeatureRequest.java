package com.bytes7.feature_flag7.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record FeatureRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no debe superar los 50 caracteres")
    String name,
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
    String description,
    @NotNull(message = "Debe indicar si la feature está habilitada por defecto")
    Boolean enabledByDefault,
    @Valid
    List<FeatureConfigRequest> configs
) {}
