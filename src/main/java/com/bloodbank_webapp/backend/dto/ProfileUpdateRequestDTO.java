package com.bloodbank_webapp.backend.dto;

import com.bloodbank_webapp.backend.model.Users;

public class ProfileUpdateRequestDTO {
    private Long userId; // Add this field
    private String firstName;
    private String lastName;
    private String gender;
    private String nationalId;
    private String bloodGroup;
    private boolean eligibilityStatus;
    private Long preferredCenterId;

    // Getter and Setter for userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public Long getPreferredCenterId() {
        return preferredCenterId;
    }

    public void setPreferredCenterId(Long preferredCenterId) {
        this.preferredCenterId = preferredCenterId;
    }

    public ProfileUpdateRequestDTO() {
    }

   public ProfileUpdateRequestDTO(String firstName, String lastName, String gender, String nationalId, String bloodGroup, boolean eligibilityStatus, Long preferredCenterId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationalId = nationalId;
        this.bloodGroup = bloodGroup;
        this.eligibilityStatus = eligibilityStatus;
        this.preferredCenterId = preferredCenterId;
    }

}
