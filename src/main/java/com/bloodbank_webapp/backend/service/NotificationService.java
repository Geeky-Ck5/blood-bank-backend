package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.model.Appointments;
import com.bloodbank_webapp.backend.model.Notification;
import com.bloodbank_webapp.backend.repository.NotificationRepository;
import com.bloodbank_webapp.backend.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.time.LocalDate;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

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
        for (Integer userId : userIds) {
            sendNotification(Long.valueOf(userId), message, Notification.NotificationType.HIGH_PRIORITY);
        }
    }

    // Method to send notification
    public void sendNotification(Long userId, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setType(Notification.NotificationType.APPOINTMENT_REMINDER);
        notification.setStatus(Notification.NotificationStatus.UNREAD);
        notificationRepository.save(notification);
        System.out.println("🔔 Notification sent to User ID: " + userId);
    }

    //  Scheduled Job to Check for Missed Appointments
    @Scheduled(cron = "0 0 8 * * ?") // Runs every day at 8 AM
    public void checkMissedAppointments() {
        System.out.println("🔎 Checking for missed appointments...");

        // Find all appointments that are in the past but not marked as completed
        List<Appointments> missedAppointments = appointmentRepository.findByDateBeforeAndStatus(
                LocalDate.now(), Appointments.AppointmentStatus.scheduled);

        for (Appointments appointment : missedAppointments) {
            Long userId = appointment.getUserId();
            sendNotification(userId, "You missed your appointment on " + appointment.getDate() + ". Please reschedule.");
        }
    }
}
