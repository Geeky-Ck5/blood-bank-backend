package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.dto.MessageDTO;
import com.bloodbank_webapp.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /** Get all messages for a user */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessageDTO>> getMessagesForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getMessagesForUser(userId));
    }

    /** Get conversation between two users */
    @GetMapping("/conversation")
    public ResponseEntity<List<MessageDTO>> getConversation(
            @RequestParam Long senderId,
            @RequestParam Long recipientId) {
        return ResponseEntity.ok(messageService.getConversation(senderId, recipientId));
    }

    /** Send a message */
    @PostMapping("/send")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO) {
        return ResponseEntity.ok(messageService.sendMessage(messageDTO));
    }

    /** Mark a message as read */
    @PutMapping("/mark-read/{messageId}")
    public ResponseEntity<MessageDTO> markMessageAsRead(@PathVariable Long messageId) {
        return ResponseEntity.ok(messageService.markAsRead(messageId));
    }

    /** Delete a message */
    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        boolean deleted = messageService.deleteMessage(messageId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
