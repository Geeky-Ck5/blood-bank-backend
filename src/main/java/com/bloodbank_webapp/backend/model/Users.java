package com.bloodbank_webapp.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role; // ENUM: 'donor', 'recipient', 'admin'

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender; // ENUM: 'male', 'female', 'other'

    @Column(name = "national_id", nullable = false, unique = true, length = 14)
    private String nationalId;

    @Column(name = "blood_group", length = 3)
    private String bloodGroup; // E.g., A+, B-, etc.

    @Column(name = "eligibility_status", nullable = false)
    private boolean eligibilityStatus;

    @Column(name = "last_donation")
    private LocalDateTime lastDonation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status; // ENUM: 'active', 'inactive'

    @Column(name = "activation_token", unique = true)
    private String activationToken;

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry;

    @Column(name = "auto_reminders", nullable = false)
    private boolean autoReminders;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "preferred_center")
    private Center preferredCenter;

    @Column(name = "created_at", nullable = false, updatable = false)
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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
        DONOR, RECIPIENT, ADMIN
    }

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum Status {
        ACTIVE, INACTIVE
    }
}
