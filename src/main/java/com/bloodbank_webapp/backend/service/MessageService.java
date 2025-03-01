package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.MessageDTO;
import com.bloodbank_webapp.backend.model.Message;
import com.bloodbank_webapp.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    /** Get all messages for a user */
    public List<MessageDTO> getMessagesForUser(Long userId) {
        List<Message> messages = messageRepository.findByRecipientId(userId);
        return messages.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /** Get conversation between two users */
    public List<MessageDTO> getConversation(Long senderId, Long recipientId) {
        List<Message> messages = messageRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        return messages.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /** Send a message */
    public MessageDTO sendMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setSenderId(messageDTO.getSenderId());
        message.setRecipientId(messageDTO.getRecipientId());
        message.setMessage(messageDTO.getMessage());
        message.setTimestamp(LocalDateTime.now());
        message.setStatus("UNREAD");

        message = messageRepository.save(message);
        return convertToDTO(message);
    }

    /** Mark a message as read */
    public MessageDTO markAsRead(Long messageId) {
        Optional<Message> messageOpt = messageRepository.findById(messageId);
        if (messageOpt.isPresent()) {
            Message message = messageOpt.get();
            message.setStatus("READ");
            message = messageRepository.save(message);
            return convertToDTO(message);
        }
        return null;
    }

    /** Delete a message */
    public boolean deleteMessage(Long messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }

    /** Convert Entity to DTO */
    private MessageDTO convertToDTO(Message message) {
        return new MessageDTO(
                message.getMessageId(),
                message.getSenderId(),
                message.getRecipientId(),
                message.getMessage(),
                message.getTimestamp(),
                message.getStatus()
        );
    }
}
