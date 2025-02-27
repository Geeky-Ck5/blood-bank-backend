package com.bloodbank_webapp.backend.dto;

import com.bloodbank_webapp.backend.model.BloodDonationEvent;

import java.time.LocalDate;

public class BloodDonationEventDTO {
    private Long eventId;
    private String eventName;
    private LocalDate eventDate;
    private Long centerId;
    private String centerName;
    private Long locationId;
    private String locationName;
    private String description;
    private String status;
    private String comments;
    private Integer expectedParticipants;

    public BloodDonationEventDTO(BloodDonationEvent event) {
        this.eventId = event.getEventId();
        this.eventName = event.getEventName();
        this.eventDate = event.getEventDate();

        // Check if event is associated with a center
        if (event.getCenter() != null) {
            this.centerId = event.getCenter().getCenterId();
            this.centerName = event.getCenter().getName();
        } else {
            this.centerId = null;
            this.centerName = "N/A";
        }

        // Check if event is associated with a blood donation location
        if (event.getLocation() != null) {
            this.locationId = event.getLocation().getLocationId();
            this.locationName = event.getLocation().getLocationName();
        } else {
            this.locationId = null;
            this.locationName = "N/A";
        }

        this.description = event.getDescription();
        this.status = event.getStatus().name();
        this.comments = event.getComments();
        this.expectedParticipants = event.getExpectedParticipants();
    }

    // Getters and Setters
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public Long getCenterId() { return centerId; }
    public void setCenterId(Long centerId) { this.centerId = centerId; }

    public String getCenterName() { return centerName; }
    public void setCenterName(String centerName) { this.centerName = centerName; }

    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public Integer getExpectedParticipants() { return expectedParticipants; }
    public void setExpectedParticipants(Integer expectedParticipants) { this.expectedParticipants = expectedParticipants; }
}
