package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.model.Notification;
import com.bloodbank_webapp.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Get all notifications for a user
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    // Send a notification to a specific user
    public Notification sendNotification(Long userId, String message, Notification.NotificationType type) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setType(type);
        notification.setStatus(Notification.NotificationStatus.UNREAD);
        return notificationRepository.save(notification);
    }

    // Mark notifications as read
    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setStatus(Notification.NotificationStatus.READ);
            notificationRepository.save(notification);
        }
    }

    // Send bulk notifications for blood shortage
    public void sendBulkNotifications(List<Integer> userIds, String message) {
        for (Long userId : userIds) {
            sendNotification(userId, message, Notification.NotificationType.HIGH_PRIORITY);
        }
    }
}
