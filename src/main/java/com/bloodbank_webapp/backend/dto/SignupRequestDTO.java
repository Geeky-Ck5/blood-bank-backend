package com.bloodbank_webapp.backend.dto;

public class SignupRequestDTO {
    private String email;
    private String password;
    private String role;

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

    public String getRole() {
        return role; // Getter for role
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role; // Setter for role
    }

    @Override
    public String toString() {
        return "SignupRequestDTO{" +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
