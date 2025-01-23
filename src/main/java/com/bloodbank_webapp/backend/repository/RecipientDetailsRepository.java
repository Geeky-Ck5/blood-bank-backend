package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.RecipientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecipientDetailsRepository extends JpaRepository<RecipientDetails, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE RecipientDetails r SET r.priority = :priority WHERE r.user.userId = :userId")
    int updateRecipientPriority(Long userId, RecipientDetails.Priority priority);

    @Query("SELECT r.priority, COUNT(r) FROM RecipientDetails r GROUP BY r.priority")
    List<Object[]> getCountGroupedByPriority();

    @Query("SELECT u.status, COUNT(u) FROM Users u WHERE u.role = 'recipient' GROUP BY u.status")
    List<Object[]> getCountGroupedByStatus();


}

