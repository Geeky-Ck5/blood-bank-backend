package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.dto.BloodDonationEventDTO;
import com.bloodbank_webapp.backend.service.BloodDonationEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class BloodDonationEventController {

    @Autowired
    private BloodDonationEventService eventService;

    @GetMapping("/upcoming")
    public ResponseEntity<List<BloodDonationEventDTO>> getUpcomingEvents() {
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }

    @GetMapping("/past")
    public ResponseEntity<List<BloodDonationEventDTO>> getPastEvents() {
        return ResponseEntity.ok(eventService.getPastEvents());
    }

    @PostMapping("/add")
    public ResponseEntity<BloodDonationEventDTO> addEvent(@RequestBody BloodDonationEventDTO eventDTO) {
        return ResponseEntity.ok(eventService.addEvent(eventDTO));
    }
}
