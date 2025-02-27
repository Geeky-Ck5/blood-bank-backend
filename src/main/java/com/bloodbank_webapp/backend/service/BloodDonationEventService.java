package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.BloodDonationEventDTO;
import com.bloodbank_webapp.backend.model.BloodDonationEvent;
import com.bloodbank_webapp.backend.model.BloodDonationLocation;
import com.bloodbank_webapp.backend.repository.BloodDonationEventRepository;
import com.bloodbank_webapp.backend.repository.BloodDonationLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloodDonationEventService {

    @Autowired
    private BloodDonationEventRepository eventRepository;

    @Autowired
    private BloodDonationLocationRepository locationRepository;

    public List<BloodDonationEventDTO> getUpcomingEvents() {
        LocalDate today = LocalDate.now();
        List<BloodDonationEvent> events = eventRepository.findByEventDateAfter(today);
        return events.stream().map(BloodDonationEventDTO::new).collect(Collectors.toList());
    }

    public List<BloodDonationEventDTO> getPastEvents() {
        LocalDate today = LocalDate.now();
        List<BloodDonationEvent> events = eventRepository.findByEventDateBefore(today);
        return events.stream().map(BloodDonationEventDTO::new).collect(Collectors.toList());
    }

    public BloodDonationEventDTO addEvent(BloodDonationEventDTO eventDTO) {
        BloodDonationLocation location = locationRepository.findById(eventDTO.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        BloodDonationEvent event = new BloodDonationEvent();
        event.setEventName(eventDTO.getEventName());
        event.setEventDate(eventDTO.getEventDate());
        event.setLocation(location);
        event.setDescription(eventDTO.getDescription());
        event.setStatus(BloodDonationEvent.EventStatus.valueOf(eventDTO.getStatus()));
        event.setExpectedParticipants(eventDTO.getExpectedParticipants());

        BloodDonationEvent savedEvent = eventRepository.save(event);
        return new BloodDonationEventDTO(savedEvent);
    }
}
