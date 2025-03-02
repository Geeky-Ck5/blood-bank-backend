package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.model.Notification;
import com.bloodbank_webapp.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Get all notifications for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    // Send a notification to a user
    @PostMapping("/send/{userId}")
    public ResponseEntity<Notification> sendNotification(
            @PathVariable Long userId,
            @RequestParam String message,
            @RequestParam Notification.NotificationType type) {
        return ResponseEntity.ok(notificationService.sendNotification(userId, message, type));
    }

    // Mark a notification as read
    @PutMapping("/read/{notificationId}")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulk-send")
    public ResponseEntity<Map<String, String>> sendBulkNotifications(@RequestBody Map<String, Object> payload) {
        // Safely extract and cast userIds list
        List<Integer> userIds = ((List<?>) payload.get("userIds"))
                .stream()
                .map(id -> Integer.parseInt(id.toString()))
                .collect(Collectors.toList());

        String message = (String) payload.get("message");

        notificationService.sendBulkNotifications(userIds, message);

        return ResponseEntity.ok(Map.of("status", "SUCCESS", "message", "Bulk notifications sent successfully."));
    }
}
