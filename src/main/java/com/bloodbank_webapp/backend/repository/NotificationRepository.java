package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Get unread notifications for a user
    List<Notification> findByUserIdAndStatus(Long userId, Notification.NotificationStatus status);

    // Get all notifications for a user
    List<Notification> findByUserId(Long userId);
}
