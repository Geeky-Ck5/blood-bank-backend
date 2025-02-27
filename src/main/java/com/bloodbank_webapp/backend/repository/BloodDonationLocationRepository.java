package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.BloodDonationLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodDonationLocationRepository extends JpaRepository<BloodDonationLocation, Long> {
}
