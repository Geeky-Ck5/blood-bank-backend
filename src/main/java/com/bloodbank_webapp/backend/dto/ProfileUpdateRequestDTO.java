package com.bloodbank_webapp.backend.dto;

import com.bloodbank_webapp.backend.model.Users;

public class ProfileUpdateRequestDTO {
    private String firstName;
    private String lastName;
    private Users.Gender gender;
    private String nationalId;
    private String bloodGroup;
    private boolean eligibilityStatus;
    private Long preferredCenterId;

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

    public Users.Gender getGender() {
        return gender;
    }

    public void setGender(Users.Gender gender) {
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
        this.gender = Users.Gender.valueOf(gender);
        this.nationalId = nationalId;
        this.bloodGroup = bloodGroup;
        this.eligibilityStatus = eligibilityStatus;
        this.preferredCenterId = preferredCenterId;
    }
}
