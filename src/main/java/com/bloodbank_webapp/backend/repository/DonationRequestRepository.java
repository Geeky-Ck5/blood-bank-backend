package com.bloodbank_webapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bloodbank_webapp.backend.model.DonationRequests;

@Repository
public interface DonationRequestRepository extends JpaRepository<DonationRequests, Long> {

    @Query("SELECT COUNT(d) FROM DonationRequests d")
    Long countDonationRequests();

    @Query("SELECT COUNT(d) FROM DonationRequests d WHERE d.status = 'APPROVED'")
    Long countApprovedRequests();

    @Query("SELECT COUNT(d) FROM DonationRequests d WHERE d.status = 'PENDING'")
    Long countPendingRequests();
}
