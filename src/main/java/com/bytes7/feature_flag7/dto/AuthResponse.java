package com.bytes7.feature_flag7.dto;

public class AuthResponse {

    private String token;
    private String tokenType = "Bearer";

    // Constructor vacío
    public AuthResponse() {}

    // Constructor
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getters y Setters
    public String getToken() { return token; }
    public String getTokenType() { return tokenType; }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

}
