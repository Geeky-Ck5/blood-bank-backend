package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByActivationToken(String activationToken);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = 'donor'")
    Long countDonors();

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = 'recipient'")
    Long countRecipients();

    @Query("SELECT u FROM Users u") // Fetch all users
    List<Users> findAllUsers();


    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.activationToken = :token, u.tokenExpiry = :expiry WHERE u.email = :email")
    void updateActivationToken(@Param("email") String email, @Param("token") String token, @Param("expiry") LocalDateTime expiry);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.status = 'ACTIVE' WHERE u.userId = :userId")
    void activateUser(@Param("userId") Long userId);

    @Query("SELECT u FROM Users u WHERE u.email = :email AND u.activationToken = :token AND u.tokenExpiry > CURRENT_TIMESTAMP")
    Optional<Users> findValidTokenByEmail(@Param("email") String email, @Param("token") String token);


}
