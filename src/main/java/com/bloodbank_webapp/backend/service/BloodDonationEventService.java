package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.model.BloodDonationEvent;
import com.bloodbank_webapp.backend.dto.BloodDonationEventDTO;
import com.bloodbank_webapp.backend.model.Center;
import com.bloodbank_webapp.backend.model.BloodDonationLocation;
import com.bloodbank_webapp.backend.repository.BloodDonationEventRepository;
import com.bloodbank_webapp.backend.repository.CenterRepository;
import com.bloodbank_webapp.backend.repository.BloodDonationLocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BloodDonationEventService {

    @Autowired
    private BloodDonationEventRepository eventRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private BloodDonationLocationRepository locationRepository;

    public List<BloodDonationEventDTO> getAllEvents() {
        List<BloodDonationEvent> events = eventRepository.findAll();
        return events.stream().map(BloodDonationEventDTO::new).collect(Collectors.toList());
    }

    public BloodDonationEvent createEvent(BloodDonationEventDTO dto) {
        BloodDonationEvent event = new BloodDonationEvent();
        event.setEventName(dto.getEventName());
        event.setEventDate(dto.getEventDate());
        event.setDescription(dto.getDescription());
        event.setStatus(BloodDonationEvent.EventStatus.valueOf(dto.getStatus()));
        event.setComments(dto.getComments());
        event.setExpectedParticipants(dto.getExpectedParticipants());

        // Ensure at least one of the two (Center or Location) is set
        if (dto.getCenterId() != null) {
            Optional<Center> center = centerRepository.findById(dto.getCenterId());
            center.ifPresent(event::setCenter);
        }

        if (dto.getLocationId() != null) {
            Optional<BloodDonationLocation> location = locationRepository.findById(dto.getLocationId());
            location.ifPresent(event::setLocation);
        }

        if (event.getCenter() == null && event.getLocation() == null) {
            throw new IllegalArgumentException("Either centerId or locationId must be provided.");
        }

        return eventRepository.save(event);
    }
}
