package com.bytes7.feature_flag7.dto;

public class RegisterRequest {

    private String username;
    private String email;
    private String password;

    // Constructor vacío
    public RegisterRequest() {}

    // Constructor
    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
