package com.bytes7.feature_flag7.controller;

import com.bytes7.feature_flag7.dto.*;
import com.bytes7.feature_flag7.model.Feature;
import com.bytes7.feature_flag7.model.FeatureConfig;
import com.bytes7.feature_flag7.repository.FeatureRepository;
import com.bytes7.feature_flag7.service.FeatureConfigService;
import com.bytes7.feature_flag7.service.FeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/features")
@Tag(name = "Features", description = "Gestión de funcionalidades")
public class FeatureController {

    private final FeatureRepository featureRepository;
    private final FeatureConfigService featureConfigService;
    private final FeatureService featureService;

    public FeatureController(FeatureRepository featureRepository, FeatureConfigService featureConfigService, FeatureService featureService) {
        this.featureRepository = featureRepository;
        this.featureConfigService = featureConfigService;
        this.featureService = featureService;
    }

    // ==============================
    // POST /api/features
    // ==============================
    @PostMapping
    @Operation(summary = "Crear nueva feature", description = "Crea una nueva funcionalidad con sus configuraciones asociadas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feature creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
public FeatureResponse createFeature(@Valid @RequestBody FeatureRequest request) {
    // Validación de nombre único
    if (featureRepository.existsByName(request.name())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de la feature ya existe");
    }
    
    Feature feature = new Feature();
    feature.setName(request.name());
    feature.setDescription(request.description());
    feature.setEnabledByDefault(request.enabledByDefault());
    
    // Configuraciones asociadas
    if (request.configs() != null) {
        request.configs().forEach(cfg -> {
            FeatureConfig fc = new FeatureConfig();
            fc.setEnvironment(cfg.environment());
            fc.setClientId(cfg.clientId());
            fc.setEnabled(cfg.enabled());
            feature.addConfig(fc);
        });
    }
    
    Feature saved = featureRepository.save(feature);
    return FeatureResponse.fromEntity(saved);
}

    // ===========================================
    // GET /api/features (con filtros opcionales)
    // ===========================================
    @GetMapping
    @Operation(
        summary = "Listar features",
        description = """
            Devuelve todas las funcionalidades registradas. 
            Se pueden aplicar filtros opcionales:
            - `enabled`: true/false → filtra por estado.
            - `name`: cadena parcial → búsqueda por nombre.
            Si no se envían parámetros, retorna todas.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de features obtenido exitosamente"),
        @ApiResponse(responseCode = "400", description = "Parámetros de filtrado inválidos"),
        @ApiResponse(responseCode = "401", description = "No autorizado, falta token JWT o es inválido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<FeatureResponse>> getFeatures(
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(featureService.getFeatures(enabled, name));
    }


    // ==============================
    // GET /api/features/{id}
    // ==============================
    @GetMapping("/{id}")
    @Operation(summary = "Extrae feature por ID", description = "Devuelve la funcionalidad registrada en el sistema de acuerdo a su ID")
    @ApiResponse(responseCode = "200", description = "Feature obtenido exitosamente")
    @ApiResponse(responseCode = "404", description = "Feature No Encontrado")
    public ResponseEntity<FeatureResponse> getFeatureById(@PathVariable String id) {
        UUID uuid = null;
        try{
            uuid = UUID.fromString(id); //Asigna el UUID a una variable dentro del try-catch valida, si no se puede transformar lanza un error
        }catch(IllegalArgumentException e) {
            System.out.println("feature No Encontrado");
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
        Feature feature = featureRepository.getReferenceById(uuid);
        return ResponseEntity.ok(FeatureResponse.fromEntity(feature));
    }

    // ==============================
    // POST /api/features/{id}/enable
    // POST /api/features/{id}/disable
    // ==============================
    @PostMapping("/{id}/enable")
    @Operation(summary = "Habilitar feature", description = "Habilita una feature para un cliente o entorno específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feature habilitada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Feature no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<FeatureConfigResponse> enableFeature(
            @PathVariable String id,
            @Valid @RequestBody FeatureToggleRequest request) {

        UUID uuid = UUID.fromString(id);
        return ResponseEntity.ok(featureConfigService.enableFeature(uuid, request));
    }

    @PostMapping("/{id}/disable")
    @Operation(summary = "Deshabilitar feature", description = "Deshabilita una feature para un cliente o entorno específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feature deshabilitada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Feature no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<FeatureConfigResponse> disableFeature(
            @PathVariable String id,
            @Valid @RequestBody FeatureToggleRequest request) {

        UUID uuid = UUID.fromString(id);
        return ResponseEntity.ok(featureConfigService.disableFeature(uuid, request));
    }

}