package com.bloodbank_webapp.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificationId")
    private Long notificationId;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "message", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type; // ENUM: APPOINTMENT_REMINDER, REQUEST_UPDATE, EVENT_REMINDER, GENERAL

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private NotificationStatus status; // ENUM: READ, UNREAD

    @Column(name = "dateCreated", nullable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    // Enum for Notification Types
    public enum NotificationType {
        APPOINTMENT_REMINDER, REQUEST_UPDATE, EVENT_REMINDER, GENERAL, HIGH_PRIORITY
    }

    // Enum for Notification Status
    public enum NotificationStatus {
        READ, UNREAD
    }

    // Getters and Setters
    public Long getNotificationId() { return notificationId; }
    public void setNotificationId(Long notificationId) { this.notificationId = notificationId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }
}
