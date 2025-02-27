package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {
    Optional<Center> findByName(String name);
}
