package com.bytes7.feature_flag7.dto;

public class LoginRequest {

    private String username;
    private String password;

    // Constructor vacío
    public LoginRequest() {}

    // Constructor
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters y Setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
