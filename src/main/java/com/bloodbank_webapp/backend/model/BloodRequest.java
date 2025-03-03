package com.bloodbank_webapp.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "BLOODREQUESTS")
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQUESTID")
    private Long requestId;


    @Column(name = "USERID", nullable = false)
    private Long userId;

    @Column(name = "BLOODGROUP", nullable = false)
    private String bloodGroup;

    @Column(name = "UNITSREQUESTED", nullable = false)
    private int unitsRequested; // Number of blood units

    @Column(name = "STATUS", nullable = true)
    @Enumerated(EnumType.STRING)
    private RequestStatus status; // PENDING, APPROVED, REJECTED, COMPLETED

    @Column(name = "PRIORITY", nullable = true)
    @Enumerated(EnumType.STRING)
    private PriorityLevel priority; // LOW, MEDIUM, HIGH

    @Column(name = "REQUESTDATE", nullable = false)
    private LocalDate requestDate;

    @Column(name = "APPROVALDATE", nullable = true)
    private LocalDate approvalDate; // Null if still pending

    // Enums for Priority and Status
    public enum PriorityLevel {
        LOW, MEDIUM, HIGH
    }

    public enum RequestStatus {
        PENDING, APPROVED, REJECTED, COMPLETED
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public PriorityLevel getPriority() {
        return priority;
    }

    public void setPriority(PriorityLevel priority) {
        this.priority = priority;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public int getUnitsRequested() {
        return unitsRequested;
    }

    public void setUnitsRequested(int unitsRequested) {
        this.unitsRequested = unitsRequested;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
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


}
