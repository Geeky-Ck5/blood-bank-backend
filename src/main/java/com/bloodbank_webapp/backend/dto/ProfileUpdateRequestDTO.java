package com.bloodbank_webapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileUpdateRequestDTO {
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("firstname") // Map "firstname" from payload
    private String firstName;

    @JsonProperty("lastname") // Map "lastname" from payload
    private String lastName;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("national_Id") // Map "national_Id" from payload
    private String nationalId;

    @JsonProperty("blood_Group") // Map "blood_Group" from payload
    private String bloodGroup;

    @JsonProperty("eligibility_status") // Map "eligibility_status" from payload
    private boolean eligibilityStatus;

    @JsonProperty("autoReminders") // Map "autoReminders" from payload
    private boolean autoReminders;

    @JsonProperty("preferred_center_id") // Map "preferred_center_id" from payload
    private Long preferredCenterId;

    // Default Constructor
    public ProfileUpdateRequestDTO() {
    }

    // Parameterized Constructor
    public ProfileUpdateRequestDTO(
            Long userId,
            String firstName,
            String lastName,
            String gender,
            String nationalId,
            String bloodGroup,
            boolean eligibilityStatus,
            boolean autoReminders,
            Long preferredCenterId
    ) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationalId = nationalId;
        this.bloodGroup = bloodGroup;
        this.eligibilityStatus = eligibilityStatus;
        this.autoReminders = autoReminders;
        this.preferredCenterId = preferredCenterId;
    }

    // Getters and Setters
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

    public boolean isAutoReminders() {
        return autoReminders;
    }

    public void setAutoReminders(boolean autoReminders) {
        this.autoReminders = autoReminders;
    }

    public Long getPreferredCenterId() {
        return preferredCenterId;
    }

    public void setPreferredCenterId(Long preferredCenterId) {
        this.preferredCenterId = preferredCenterId;
    }
}
