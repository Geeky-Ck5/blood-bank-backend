package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.model.Recipients;
import com.bloodbank_webapp.backend.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/recipients")
public class RecipientController {

    @Autowired
    private RecipientService recipientService;

    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, String>> updateRecipientStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusRequest) {
        String newStatus = statusRequest.get("status");

        try {
            Recipients updatedRecipient = recipientService.updateRecipientStatus(id, newStatus);
            return ResponseEntity.ok(Map.of(
                    "message", "Recipient status updated successfully.",
                    "recipientId", String.valueOf(updatedRecipient.getRecipientId()),
                    "newStatus", updatedRecipient.getStatus()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }


    @PutMapping("/{id}/priority")
    public ResponseEntity<Map<String, String>> updateRecipientPriority(
            @PathVariable Long id,
            @RequestBody Map<String, String> priorityRequest) {
        String newPriority = priorityRequest.get("priority");

        try {
            Recipients updatedRecipient = recipientService.updateRecipientPriority(id, newPriority);
            return ResponseEntity.ok(Map.of(
                    "message", "Recipient priority updated successfully.",
                    "recipientId", String.valueOf(updatedRecipient.getRecipientId()),
                    "newPriority", updatedRecipient.getPriority()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }
}
