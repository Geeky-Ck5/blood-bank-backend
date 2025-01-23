package com.bloodbank_webapp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bloodbank_webapp.backend.repository.UserRepository;
import com.bloodbank_webapp.backend.repository.DonationRequestRepository;
import com.bloodbank_webapp.backend.repository.BloodInventoryRepository;

import java.util.Map;
import java.util.Optional;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationRequestRepository donationRequestRepository;

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    public Map<String, Object> getGeneralStatistics() {
        Long totalDonors = Optional.ofNullable(userRepository.countDonors()).orElse(0L);
        Long totalRecipients = Optional.ofNullable(userRepository.countRecipients()).orElse(0L);
        Long totalBloodUnitsAvailable = Optional.ofNullable(bloodInventoryRepository.totalBloodUnits()).orElse(0L);
        Long totalDonationRequests = Optional.ofNullable(donationRequestRepository.countDonationRequests()).orElse(0L);
        Long totalApprovedRequests = Optional.ofNullable(donationRequestRepository.countApprovedRequests()).orElse(0L);
        Long totalPendingRequests = Optional.ofNullable(donationRequestRepository.countPendingRequests()).orElse(0L);

        return Map.of(
                "totalDonors", totalDonors,
                "totalRecipients", totalRecipients,
                "totalBloodUnitsAvailable", totalBloodUnitsAvailable,
                "totalDonationRequests", totalDonationRequests,
                "totalApprovedRequests", totalApprovedRequests,
                "totalPendingRequests", totalPendingRequests
        );
    }
}
