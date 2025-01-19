package com.bloodbank_webapp.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contact_info")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id", nullable = false)
    private Long contactId;

    @Column(name = "phone_number", length = 7)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "mobile_operator", nullable = false)
    private MobileOperator mobileOperator; // ENUM: 'EMTEL', 'MYTMU', 'CHILLI', 'OTHER'

    @Column(name = "mobile_number", length = 8, nullable = false)
    private String mobileNumber;

    @Column(name = "street_address", length = 255)
    private String streetAddress;

    @Column(name = "address_line_1", length = 255)
    private String addressLine1;

    @Column(name = "address_line_2", length = 255)
    private String addressLine2;

    @Column(name = "address_line_3", length = 255)
    private String addressLine3;

    @Column(name = "city", length = 255)
    private String city;

    @Column(name = "district", length = 50)
    private String district;

    @Column(name = "country", length = 50)
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

    public MobileOperator getMobileOperator() {
        return mobileOperator;
    }

    public void setMobileOperator(MobileOperator mobileOperator) {
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

    public enum MobileOperator {
        EMTEL, MYTMU, CHILLI, OTHER
    }
}
