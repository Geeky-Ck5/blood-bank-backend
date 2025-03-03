package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.BloodRequestDTO;
import com.bloodbank_webapp.backend.model.BloodRequest;
import com.bloodbank_webapp.backend.model.Users;
import com.bloodbank_webapp.backend.repository.BloodRequestRepository;
import com.bloodbank_webapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloodRequestService {

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private UserRepository userRepository;

    // Submit a blood request
    public BloodRequestDTO submitBloodRequest(Long userId, BloodRequestDTO dto) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        BloodRequest request = new BloodRequest();
        request.setUserId(userId);
        request.setBloodGroup(dto.getBloodGroup());
        request.setPriority(BloodRequest.PriorityLevel.valueOf(dto.getPriority()));
        request.setStatus(BloodRequest.RequestStatus.PENDING);
        request.setUnitsRequested(dto.getUnitsRequested());
        request.setRequestDate(LocalDate.now());
        request.setApprovalDate(null); // Initially NULL

        BloodRequest savedRequest = bloodRequestRepository.save(request);
        return new BloodRequestDTO(savedRequest);
    }

    // Get blood request history for a user
    public List<BloodRequestDTO> getBloodRequestHistory(Long userId) {
        return bloodRequestRepository.findByUserUserId(userId)
                .stream().map(BloodRequestDTO::new)
                .collect(Collectors.toList());
    }

    // Get blood request statistics for Admin
    public List<Object[]> getRequestCountByBloodGroup() {
        return bloodRequestRepository.getRequestCountByBloodGroup();
    }

    public List<Object[]> getRequestCountByUser() {
        return bloodRequestRepository.getRequestCountByUser();
    }

    public List<Object[]> getRequestCountByPriority() {
        return bloodRequestRepository.getRequestCountByPriority();
    }

    public List<Object[]> getRequestStatusSummary() {
        return bloodRequestRepository.getRequestStatusSummary();
    }
}
