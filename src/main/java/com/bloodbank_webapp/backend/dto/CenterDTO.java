package com.bloodbank_webapp.backend.dto;

import com.bloodbank_webapp.backend.model.Center;

public class CenterDTO {
    private Long centerId;
    private String name;
    private String address;  // ✅ Use "address" instead of "streetAddress"
    private String city;
    private String district;
    private String country;
    private String contactNumber;
    private Double longitude;  // 🆕 Added
    private Double latitude;

    // ✅ Constructor to map entity to DTO
    public CenterDTO(Center center) {
        this.centerId = center.getCenterId();
        this.name = center.getName();
        this.address = center.getAddress();  // ✅ Fix: Use address
        this.city = center.getCity();
        this.district = center.getDistrict();
        this.country = center.getCountry();
        this.contactNumber = center.getContactNumber();
        this.longitude = Double.valueOf(center.getLongitude());  // 🆕 Map longitude
        this.latitude = Double.valueOf(center.getLatitude());
    }

    // ✅ Getters and Setters
    public Long getCenterId() { return centerId; }
    public void setCenterId(Long centerId) { this.centerId = centerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }  // ✅ Fix: Use address
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
}
