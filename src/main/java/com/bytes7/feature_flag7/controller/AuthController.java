package com.bytes7.feature_flag7.controller;

import com.bytes7.feature_flag7.dto.LoginRequest;
import com.bytes7.feature_flag7.dto.LoginResponse;
import com.bytes7.feature_flag7.dto.RegisterRequest;
import com.bytes7.feature_flag7.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para registro y login de usuarios.")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registro de Usuario", description = "Registra un nuevo usuario con rol USER")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Usuario registrado con éxito.");
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Autentica usuario y devuelve token JWT")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
