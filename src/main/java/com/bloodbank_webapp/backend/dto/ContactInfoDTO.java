package com.bloodbank_webapp.backend.dto;

import com.bloodbank_webapp.backend.model.ContactInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactInfoDTO {
    private Long contactId;
    private String phoneNumber;

    @JsonProperty("mobile_operator")
    private String mobileOperator;

    @JsonProperty("mobile_number")
    private String mobileNumber;
      private String streetAddress;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String district;
    private String country;

    // Getters and Setters
    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileOperator() {
        return mobileOperator;
    }

    public void setMobileOperator(String mobileOperator) {
        this.mobileOperator = mobileOperator;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private static ContactInfo.MobileOperator getValidMobileOperator(String operator) {
        if (operator == null || operator.isEmpty()) {
            throw new RuntimeException("Mobile operator cannot be null or empty");
        }

        try {
            // Normalize to lowercase to match the enum definition
            return ContactInfo.MobileOperator.valueOf(operator.toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid mobile operator: " + operator);
        }
    }

    public static ContactInfo mapDtoToEntity(ContactInfoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ContactInfoDTO cannot be null");
        }

        ContactInfo contactInfo = new ContactInfo();

        // Map simple string fields
        contactInfo.setContactId(dto.getContactId());
        contactInfo.setPhoneNumber(dto.getPhoneNumber());
        contactInfo.setMobileNumber(dto.getMobileNumber());
        contactInfo.setStreetAddress(dto.getStreetAddress());
        contactInfo.setAddressLine1(dto.getAddressLine1());
        contactInfo.setAddressLine2(dto.getAddressLine2());
        contactInfo.setAddressLine3(dto.getAddressLine3());
        contactInfo.setCity(dto.getCity());
        contactInfo.setDistrict(dto.getDistrict());
        contactInfo.setCountry(dto.getCountry());

        // Normalize mobile operator and map to enum
        if (dto.getMobileOperator() != null) {
            try {
                contactInfo.setMobileOperator(
                        ContactInfo.MobileOperator.valueOf(dto.getMobileOperator())
                );

            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid mobile operator: " + dto.getMobileOperator());
            }
        }
            return contactInfo;
    }
}
