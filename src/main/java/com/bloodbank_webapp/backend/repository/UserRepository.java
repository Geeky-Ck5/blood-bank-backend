package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByActivationToken(String activationToken);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = 'donor'")
    Long countDonors();

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = 'recipient'")
    Long countRecipients();

}
