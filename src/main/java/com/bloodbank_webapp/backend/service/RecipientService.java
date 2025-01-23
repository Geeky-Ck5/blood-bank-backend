package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.model.Recipients;
import com.bloodbank_webapp.backend.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RecipientService {

    @Autowired
    private RecipientRepository recipientRepository;

    public Recipients updateRecipientStatus(Long recipientId, String status) {
        Optional<Recipients> optionalRecipient = recipientRepository.findById(recipientId);

        if (optionalRecipient.isEmpty()) {
            throw new RuntimeException("Recipient not found with ID: " + recipientId);
        }

        Recipients recipient = optionalRecipient.get();
        recipient.setStatus(status);
        return recipientRepository.save(recipient);
    }

    public Recipients updateRecipientPriority(Long recipientId, String priority) {
        // Validate priority
        if (!List.of("High", "Medium", "Low").contains(priority)) {
            throw new IllegalArgumentException("Invalid priority value. Allowed values are: High, Medium, Low.");
        }

        // Fetch recipient
        Recipients recipient = recipientRepository.findById(recipientId)
                .orElseThrow(() -> new RuntimeException("Recipient not found with ID: " + recipientId));

        // Update priority
        recipient.setPriority(priority);
        return recipientRepository.save(recipient);
    }

}



