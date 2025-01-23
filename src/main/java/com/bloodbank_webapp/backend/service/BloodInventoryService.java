package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.repository.BloodInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BloodInventoryService {

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    public List<BloodGroupSummaryDTO> getBloodInventorySummary() {
        return bloodInventoryRepository.getBloodInventorySummary().stream()
                .map(summary -> new BloodGroupSummaryDTO(summary.getBloodGroup(), summary.getUnitsAvailable()))
                .collect(Collectors.toList());
    }

    public static class BloodGroupSummaryDTO {
        private String bloodGroup;
        private Long unitsAvailable;

        public BloodGroupSummaryDTO(String bloodGroup, Long unitsAvailable) {
            this.bloodGroup = bloodGroup;
            this.unitsAvailable = unitsAvailable;
        }

        public String getBloodGroup() {
            return bloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public Long getUnitsAvailable() {
            return unitsAvailable;
        }

        public void setUnitsAvailable(Long unitsAvailable) {
            this.unitsAvailable = unitsAvailable;
        }
    }

    public List<Map<String, Object>> getExpiryAlerts() {
        // Fetch data from the repository
        List<Object[]> results = bloodInventoryRepository.findBloodUnitsExpiringSoon();

        // Format the results
        List<Map<String, Object>> alerts = new ArrayList<>();
        for (Object[] row : results) {
            String bloodGroup = (String) row[0];
            Long unitsExpiringSoon = (Long) row[1];
            LocalDateTime expiryDate = (LocalDateTime) row[2];

            Map<String, Object> alert = new HashMap<>();
            alert.put("bloodGroup", bloodGroup);
            alert.put("unitsExpiringSoon", unitsExpiringSoon);
            alert.put("expiryDate", expiryDate);
            alerts.add(alert);
        }

        return alerts;
    }
}
