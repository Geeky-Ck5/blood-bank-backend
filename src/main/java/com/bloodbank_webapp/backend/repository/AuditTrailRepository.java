package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {
    List<AuditTrail> findByUserId(Long userId);
    List<AuditTrail> findByActionPerformed(String actionPerformed);
    List<AuditTrail> findByUserRole(String userRole);
}
