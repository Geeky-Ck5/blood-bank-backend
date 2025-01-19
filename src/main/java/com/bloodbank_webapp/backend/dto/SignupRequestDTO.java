package com.bloodbank_webapp.backend.dto;

public class SignupRequestDTO {
    private String email;
    private String password;

    // Default constructor
    public SignupRequestDTO() {
    }

    // Constructor with fields
    public SignupRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
