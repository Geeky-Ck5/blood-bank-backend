package com.bloodbank_webapp.backend.dto;

import com.bloodbank_webapp.backend.model.BloodDonationEvent;
import java.time.LocalDate;

public class BloodDonationEventDTO {

    private Long eventId;
    private String eventName;
    private LocalDate eventDate;
    private Long locationId;
    private String locationName;
    private String description;
    private String status;
    private Integer expectedParticipants;

    public BloodDonationEventDTO() {}

    public BloodDonationEventDTO(BloodDonationEvent event) {
        this.eventId = event.getEventId();
        this.eventName = event.getEventName();
        this.eventDate = event.getEventDate();
        this.locationId = event.getLocation().getLocationId();
        this.locationName = event.getLocation().getLocationName();
        this.description = event.getDescription();
        this.status = event.getStatus().name();
        this.expectedParticipants = event.getExpectedParticipants();
    }

    public Integer getExpectedParticipants() {
        return expectedParticipants;
    }

    public void setExpectedParticipants(Integer expectedParticipants) {
        this.expectedParticipants = expectedParticipants;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }



}
