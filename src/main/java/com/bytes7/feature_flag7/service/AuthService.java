package com.bytes7.feature_flag7.service;

import com.bytes7.feature_flag7.dto.LoginRequest;
import com.bytes7.feature_flag7.dto.LoginResponse;
import com.bytes7.feature_flag7.dto.RegisterRequest;
import com.bytes7.feature_flag7.model.User;
import com.bytes7.feature_flag7.repository.UserRepository;
import com.bytes7.feature_flag7.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    // Registro de usuario
    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadCredentialsException("Username ya existe");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadCredentialsException("Email ya existe");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);
    }

    // Login de usuario
    public LoginResponse login(LoginRequest request) {
        System.out.println("Username: " + request.getUsername());
        System.out.println("Password raw: " + request.getPassword());
                
        // Creamos el token de autenticación
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        // Validamos credenciales con AuthenticationManager
        authenticationManager.authenticate(authToken);

        // Si pasa la autenticación, obtenemos el usuario
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        System.out.println("Encoded password in DB: " + user.getPassword());

        // Generamos JWT
        String token = jwtUtils.generateToken(user.getUsername(), user.getRole());

        return new LoginResponse(token);
    }
}
