package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.Recipients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientRepository extends JpaRepository<Recipients, Long> {
}