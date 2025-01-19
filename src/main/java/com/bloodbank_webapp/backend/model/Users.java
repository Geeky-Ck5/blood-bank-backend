package com.bloodbank_webapp.backend.model;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERID", nullable = false)
    private Long userId;

    @Column(name = "FIRSTNAME", nullable = true, length = 100)
    private String firstName;

    @Column(name = "LASTNAME", nullable = true, length = 100)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "PASSWORDHASH", nullable = false)
    private String password;

    @Column(name = "ROLE", nullable = false)
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", nullable = true)
    private Gender gender; // ENUM: 'male', 'female', 'other'

    @Column(name = "NATIONAL_ID", nullable = true, unique = true, length = 14)
    private String nationalId;

    @Column(name = "BLOOD_GROUP",nullable = true, length = 3)
    private String bloodGroup; // E.g., A+, B-, etc.

    @Column(name = "ELIGIBILITY_STATUS", nullable = true)
    private boolean eligibilityStatus;

    @Column(name = "LAST_DONATION",nullable = true)
    private LocalDateTime lastDonation;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = true)
    private Status status; // ENUM: 'active', 'inactive'

    @Column(name = "ACTIVATION_TOKEN",nullable = true, unique = true)
    private String activationToken;

    @Column(name = "TOKEN_EXPIRY",nullable = true)
    private LocalDateTime tokenExpiry;

    @Column(name = "AUTO_REMINDERS", nullable = true)
    private boolean autoReminders;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PREFERRED_CENTER", nullable = true)
    private Center preferredCenter;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public boolean isEligibilityStatus() {
        return eligibilityStatus;
    }

    public void setEligibilityStatus(boolean eligibilityStatus) {
        this.eligibilityStatus = eligibilityStatus;
    }

    public LocalDateTime getLastDonation() {
        return lastDonation;
    }

    public void setLastDonation(LocalDateTime lastDonation) {
        this.lastDonation = lastDonation;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(LocalDateTime tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public boolean isAutoReminders() {
        return autoReminders;
    }

    public void setAutoReminders(boolean autoReminders) {
        this.autoReminders = autoReminders;
    }

    public Center getPreferredCenter() {
        return preferredCenter;
    }

    public void setPreferredCenter(Center preferredCenter) {
        this.preferredCenter = preferredCenter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", gender=" + gender +
                ", nationalId='" + nationalId + '\'' +
                '}';
    }

    public enum Role {
        donor, recipient, admin
    }

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum Status {
        ACTIVE, INACTIVE
    }
}
