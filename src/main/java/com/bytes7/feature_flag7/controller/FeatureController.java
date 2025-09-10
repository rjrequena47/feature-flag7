package com.bytes7.feature_flag7.controller;

import com.bytes7.feature_flag7.dto.*;
import com.bytes7.feature_flag7.model.Feature;
import com.bytes7.feature_flag7.model.FeatureConfig;
import com.bytes7.feature_flag7.repository.FeatureRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/features")
@Tag(name = "Features", description = "Gestión de funcionalidades")
public class FeatureController {

    private final FeatureRepository featureRepository;

    public FeatureController(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
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

    // ==============================
    // GET /api/features
    // ==============================
    @GetMapping
    @Operation(summary = "Listar todas las features", description = "Devuelve todas las funcionalidades registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Listado de features obtenido exitosamente")
    @ApiResponse(responseCode = "404", description = "Features No Encontrados")
    public ResponseEntity<List<FeatureResponse>> getAllFeatures() {
        Boolean empty = false;
       try {
           empty = featureRepository.findAll().isEmpty();
           if (empty) throw new RuntimeException("Features No Encontrados");
       }catch (Exception e) {
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(featureRepository.findAll().stream().map(FeatureResponse::fromEntity).toList());
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

}
