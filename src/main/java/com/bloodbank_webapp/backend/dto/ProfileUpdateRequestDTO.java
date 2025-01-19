package com.bloodbank_webapp.backend.dto;

import com.bloodbank_webapp.backend.model.Users;

public class ProfileUpdateRequestDTO {
    private String firstName;

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

    private String lastName;
    private Users.Gender gender;
    private String nationalId;
    private String bloodGroup;

    public ProfileUpdateRequestDTO() {
    }

    public ProfileUpdateRequestDTO(String firstName, String lastName, String gender, String nationalId, String bloodGroup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = Users.Gender.valueOf(gender);
        this.nationalId = nationalId;
        this.bloodGroup = bloodGroup;
    }
}
