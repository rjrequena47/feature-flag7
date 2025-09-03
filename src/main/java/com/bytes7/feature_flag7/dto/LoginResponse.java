package com.bytes7.feature_flag7.dto;

public class LoginResponse {

    private String token;

    // Constructor vacío
    public LoginResponse() {}

    // Constructor
    public LoginResponse(String token) {
        this.token = token;
    }

    // Getter y Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
