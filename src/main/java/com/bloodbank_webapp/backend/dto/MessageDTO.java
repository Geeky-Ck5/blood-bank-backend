package com.bloodbank_webapp.backend.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private Long messageId;
    private Long senderId;
    private Long recipientId;
    private String message;
    private LocalDateTime timestamp;
    private String status;

    // Constructor
    public MessageDTO() {}

    public MessageDTO(Long messageId, Long senderId, Long recipientId, String message, LocalDateTime timestamp, String status) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }

    // Getters and Setters
    public Long getMessageId() { return messageId; }
    public void setMessageId(Long messageId) { this.messageId = messageId; }

    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public Long getRecipientId() { return recipientId; }
    public void setRecipientId(Long recipientId) { this.recipientId = recipientId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
