package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.BloodDonationEventDTO;
import com.bloodbank_webapp.backend.model.BloodDonationEvent;
import com.bloodbank_webapp.backend.model.BloodDonationLocation;
import com.bloodbank_webapp.backend.model.Center;
import com.bloodbank_webapp.backend.repository.BloodDonationEventRepository;
import com.bloodbank_webapp.backend.repository.BloodDonationLocationRepository;
import com.bloodbank_webapp.backend.repository.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    /**
     * ✅ Fetch all events
     */
    public List<BloodDonationEventDTO> getAllEvents() {
        List<BloodDonationEvent> events = eventRepository.findAll();
        return events.stream().map(BloodDonationEventDTO::new).collect(Collectors.toList());
    }

    /**
     * ✅ Fetch a single event by ID
     */
    public BloodDonationEventDTO getEventById(Long eventId) {
        Optional<BloodDonationEvent> event = eventRepository.findById(eventId);
        return event.map(BloodDonationEventDTO::new).orElse(null);
    }

    /**
     * ✅ Add a new event
     */
    public BloodDonationEventDTO addEvent(BloodDonationEventDTO eventDTO) {
        BloodDonationEvent event = new BloodDonationEvent();
        event.setEventName(eventDTO.getEventName());
        event.setEventDate(eventDTO.getEventDate());
        event.setDescription(eventDTO.getDescription());
        event.setStatus(BloodDonationEvent.EventStatus.valueOf(eventDTO.getStatus()));
        event.setComments(eventDTO.getComments());
        event.setExpectedParticipants(eventDTO.getExpectedParticipants());

        // Handle location or center
        if (eventDTO.getLocationId() != null) {
            Optional<BloodDonationLocation> location = locationRepository.findById(eventDTO.getLocationId());
            location.ifPresent(event::setLocation);
        }

        if (eventDTO.getCenterId() != null) {
            Optional<Center> center = centerRepository.findById(eventDTO.getCenterId());
            center.ifPresent(event::setCenter);
        }

        BloodDonationEvent savedEvent = eventRepository.save(event);
        return new BloodDonationEventDTO(savedEvent);
    }

    /**
     * ✅ Update an existing event
     */
    public BloodDonationEventDTO updateEvent(Long eventId, BloodDonationEventDTO eventDTO) {
        Optional<BloodDonationEvent> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            BloodDonationEvent event = existingEvent.get();
            event.setEventName(eventDTO.getEventName());
            event.setEventDate(eventDTO.getEventDate());
            event.setDescription(eventDTO.getDescription());
            event.setStatus(BloodDonationEvent.EventStatus.valueOf(eventDTO.getStatus()));
            event.setComments(eventDTO.getComments());
            event.setExpectedParticipants(eventDTO.getExpectedParticipants());

            if (eventDTO.getLocationId() != null) {
                Optional<BloodDonationLocation> location = locationRepository.findById(eventDTO.getLocationId());
                location.ifPresent(event::setLocation);
            } else {
                event.setLocation(null);
            }

            if (eventDTO.getCenterId() != null) {
                Optional<Center> center = centerRepository.findById(eventDTO.getCenterId());
                center.ifPresent(event::setCenter);
            } else {
                event.setCenter(null);
            }

            BloodDonationEvent updatedEvent = eventRepository.save(event);
            return new BloodDonationEventDTO(updatedEvent);
        }
        return null;
    }

    /**
     * ✅ Delete an event
     */
    public boolean deleteEvent(Long eventId) {
        if (eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
            return true;
        }
        return false;
    }



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

}
