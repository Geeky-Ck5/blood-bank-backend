package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.model.AuditTrail;
import com.bloodbank_webapp.backend.service.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-trail")
public class AuditTrailController {

    @Autowired
    private AuditTrailService auditTrailService;

    // Get all logs
    @GetMapping
    public ResponseEntity<List<AuditTrail>> getAllLogs() {
        return ResponseEntity.ok(auditTrailService.getAllLogs());
    }

    // Get logs by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditTrail>> getLogsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(auditTrailService.getLogsByUserId(userId));
    }

    // Get logs by action
    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditTrail>> getLogsByAction(@PathVariable String action) {
        return ResponseEntity.ok(auditTrailService.getLogsByAction(action));
    }

    // Get logs by user role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<AuditTrail>> getLogsByUserRole(@PathVariable String role) {
        return ResponseEntity.ok(auditTrailService.getLogsByUserRole(role));
    }

    // Save a new audit log
    @PostMapping
    public ResponseEntity<AuditTrail> saveLog(@RequestBody AuditTrail log) {
        return ResponseEntity.ok(auditTrailService.createAuditLog(log));
    }
}
