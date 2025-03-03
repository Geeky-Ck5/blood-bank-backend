package com.bloodbank_webapp.backend.dto;

import com.bloodbank_webapp.backend.model.BloodRequest;
import java.time.LocalDate;

public class BloodRequestDTO {
    private Long requestId;
    private Long userId;
    private String bloodGroup;
    private String priority;
    private String status;
    private int unitsRequested;
    private LocalDate requestDate;
    private LocalDate approvalDate;


    public BloodRequestDTO(BloodRequest request) {
        this.requestId = request.getRequestId();
        this.userId = request.getUserId();
        this.bloodGroup = request.getBloodGroup();
        this.priority = (request.getPriority() != null) ? request.getPriority().name() : "LOW";
        this.status = request.getStatus().name();
        this.unitsRequested = request.getUnitsRequested();
        this.requestDate = request.getRequestDate();
        this.approvalDate = request.getApprovalDate();
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUnitsRequested() {
        return unitsRequested;
    }

    public void setUnitsRequested(int unitsRequested) {
        this.unitsRequested = unitsRequested;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }
}
