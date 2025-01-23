package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.service.BloodInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blood-inventory")
public class BloodInventoryController {

    @Autowired
    private BloodInventoryService bloodInventoryService;

    @GetMapping("/summary")
    public ResponseEntity<List<BloodInventoryService.BloodGroupSummaryDTO>> getBloodInventorySummary() {
        List<BloodInventoryService.BloodGroupSummaryDTO> summary = bloodInventoryService.getBloodInventorySummary();
        return ResponseEntity.ok(summary);
    }


    @GetMapping("/expiry-alerts")
    public ResponseEntity<List<Map<String, Object>>> getExpiryAlerts() {
        // Fetch the expiry alerts
        List<Map<String, Object>> alerts = bloodInventoryService.getExpiryAlerts();

        return ResponseEntity.ok(alerts);
    }
}
