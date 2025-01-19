package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {
}
