package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.dto.BloodRequestDTO;
import com.bloodbank_webapp.backend.service.BloodRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blood-requests")
public class BloodRequestController {

    @Autowired
    private BloodRequestService bloodRequestService;

    // Submit a blood request
    @PostMapping("/submit/{userId}")
    public ResponseEntity<BloodRequestDTO> submitBloodRequest(
            @PathVariable Long userId, @RequestBody BloodRequestDTO requestDTO) {
        return ResponseEntity.ok(bloodRequestService.submitBloodRequest(userId, requestDTO));
    }

    // Get blood request history for a user
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<BloodRequestDTO>> getBloodRequestHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(bloodRequestService.getBloodRequestHistory(userId));
    }

    // Admin Dashboard: Total requests per blood group
    @GetMapping("/stats/blood-group")
    public ResponseEntity<Map<String, Long>> getRequestCountByBloodGroup() {
        return ResponseEntity.ok(bloodRequestService.getRequestCountByBloodGroup()
                .stream().collect(Collectors.toMap(data -> (String) data[0], data -> (Long) data[1])));
    }

    // Admin Dashboard: Total requests per user
    @GetMapping("/stats/user")
    public ResponseEntity<Map<Long, Long>> getRequestCountByUser() {
        return ResponseEntity.ok(bloodRequestService.getRequestCountByUser()
                .stream().collect(Collectors.toMap(data -> (Long) data[0], data -> (Long) data[1])));
    }

    // Admin Dashboard: Requests by priority
    @GetMapping("/stats/priority")
    public ResponseEntity<Map<String, Long>> getRequestCountByPriority() {
        return ResponseEntity.ok(bloodRequestService.getRequestCountByPriority()
                .stream().collect(Collectors.toMap(data -> data[0].toString(), data -> (Long) data[1])));
    }
}
