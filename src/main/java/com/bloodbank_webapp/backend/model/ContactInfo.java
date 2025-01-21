package com.bloodbank_webapp.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contact_info")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTACT_ID")
    private Long contactId;

    @Column(name = "PHONE_NUMBER", length = 7, nullable = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "MOBILE_OPERATOR", nullable = true)
    private MobileOperator mobileOperator;

    @Column(name = "MOBILE_NUMBER", length = 8, nullable = true)
    private String mobileNumber;

    @Column(name = "STREET_ADDRESS", length = 255, nullable = true)
    private String streetAddress;

    @Column(name = "ADDRESS_LINE_1", length = 255, nullable = true)
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2", length = 255, nullable = true)
    private String addressLine2;

    @Column(name = "ADDRESS_LINE_3", length = 255, nullable = true)
    private String addressLine3;

    @Column(name = "CITY", length = 255, nullable = true)
    private String city;

    @Column(name = "DISTRICT", length = 50, nullable = true)
    private String district;

    @Column(name = "COUNTRY", length = 50, nullable = true)
    private String country;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", nullable = false)
    private Users user;

    public enum MobileOperator {
        EMTEL, MYTMU, CHILLI, OTHER
    }

    // Getters AND Setters
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }


}
