package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.BloodDonationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BloodDonationEventRepository extends JpaRepository<BloodDonationEvent, Long> {
    List<BloodDonationEvent> findByEventDateAfter(LocalDate date);
    List<BloodDonationEvent> findByEventDateBefore(LocalDate date);
}
