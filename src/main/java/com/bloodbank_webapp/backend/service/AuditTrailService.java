package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.model.AuditTrail;
import com.bloodbank_webapp.backend.repository.AuditTrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditTrailService {

    @Autowired
    private AuditTrailRepository auditTrailRepository;

    public List<AuditTrail> getAllLogs() {
        return auditTrailRepository.findAll();
    }

    public List<AuditTrail> getLogsByUserId(Long userId) {
        return auditTrailRepository.findByUserId(userId);
    }

    public List<AuditTrail> getLogsByAction(String actionPerformed) {
        return auditTrailRepository.findByActionPerformed(actionPerformed);
    }

    public List<AuditTrail> getLogsByUserRole(String userRole) {
        return auditTrailRepository.findByUserRole(userRole);
    }

    public AuditTrail createAuditLog(AuditTrail log) {
        log.setActionDatetime(LocalDateTime.now()); // Set current time
        return auditTrailRepository.save(log);
    }
}
