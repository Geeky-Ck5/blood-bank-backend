package com.bloodbank_webapp.backend.dto;

import com.bloodbank_webapp.backend.model.BloodDonationLocation;

public class BloodDonationLocationDTO {

    private Long locationId;
    private String locationName;
    private String address;
    private Double latitude;
    private Double longitude;
    private String contactNumber;

    public BloodDonationLocationDTO() {}

    public BloodDonationLocationDTO(BloodDonationLocation location) {
        this.locationId = location.getLocationId();
        this.locationName = location.getLocationName();
        this.address = location.getAddress();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.contactNumber = location.getContactNumber();
    }

    // Getters and Setters
    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}
