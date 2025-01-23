package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.model.RecipientDetails;
import com.bloodbank_webapp.backend.repository.RecipientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipientDetailsService {

    @Autowired
    private RecipientDetailsRepository recipientDetailsRepository;

    public void updatePriority(Long userId, String priority) {
        // Validate and map priority (case-insensitive)
        RecipientDetails.Priority priorityEnum;
        try {
            priorityEnum = RecipientDetails.Priority.fromString(priority); // Accepts lowercase inputs
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid priority value. Allowed values are: low, medium, high.");
        }

        // Call repository to update priority
        int updatedRows = recipientDetailsRepository.updateRecipientPriority(userId, priorityEnum);
        if (updatedRows == 0) {
            throw new RuntimeException("Recipient not found or priority not updated for userId: " + userId);
        }
    }


    public Map<String, Map<String, Long>> getRecipientStats() {
        // Get counts grouped by priority
        List<Object[]> priorityCounts = recipientDetailsRepository.getCountGroupedByPriority();
        Map<String, Long> priorityStats = new HashMap<>();
        for (Object[] row : priorityCounts) {
            String priority = ((String) row[0]); // Normalize to uppercase
            Long count = (Long) row[1];
            priorityStats.put(priority, count);
        }

        // Get counts grouped by status
        List<Object[]> statusCounts = recipientDetailsRepository.getCountGroupedByStatus();
        Map<String, Long> statusStats = new HashMap<>();
        for (Object[] row : statusCounts) {
            String status = (String) row[0];
            Long count = (Long) row[1];
            statusStats.put(status, count);
        }

        // Combine both stats
        Map<String, Map<String, Long>> response = new HashMap<>();
        response.put("priorityStats", priorityStats);
        response.put("statusStats", statusStats);

        return response;
    }

}
