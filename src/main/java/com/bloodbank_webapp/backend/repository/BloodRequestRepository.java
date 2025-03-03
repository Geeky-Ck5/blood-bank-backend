package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {

    // Get blood request history for a user
    List<BloodRequest> findByUserUserId(Long userId);

    // Get total requests per blood group
    @Query("SELECT r.bloodGroup, COUNT(r) FROM BloodRequest r GROUP BY r.bloodGroup")
    List<Object[]> getRequestCountByBloodGroup();

    // Get total requests per user
    @Query("SELECT r.userId, COUNT(r) FROM BloodRequest r GROUP BY r.user.userId")
    List<Object[]> getRequestCountByUser();

    // Get total requests by priority
    @Query("SELECT r.priority, COUNT(r) FROM BloodRequest r GROUP BY r.priority")
    List<Object[]> getRequestCountByPriority();

    // Get pending/completed requests
    @Query("SELECT r.status, COUNT(r) FROM BloodRequest r GROUP BY r.status")
    List<Object[]> getRequestStatusSummary();
}
