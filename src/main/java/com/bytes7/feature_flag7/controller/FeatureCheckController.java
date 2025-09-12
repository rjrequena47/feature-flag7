package com.bytes7.feature_flag7.controller;

import com.bytes7.feature_flag7.backend.enums.Environment;
import com.bytes7.feature_flag7.service.FeatureCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/features")
@Tag(name = "Check", description = "Verificador de estado de la feature")
public class FeatureCheckController {

    private final FeatureCheckService featureCheckService;

    public FeatureCheckController(FeatureCheckService featureCheckService) {
        this.featureCheckService = featureCheckService;
    }

    @GetMapping("/check")
    @Operation(summary = "Verifica si la feature esta activa", description = "Devuelve true o false")
    public boolean checkFeature(
            @RequestParam String feature,
            @RequestParam String clientId,
            @RequestParam Environment env) {
        return featureCheckService.isFeatureActive(feature, clientId, env);
    }
}
