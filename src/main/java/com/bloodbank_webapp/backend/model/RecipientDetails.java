package com.bloodbank_webapp.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "RECIPIENTDETAILS")
public class RecipientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECIPIENTID", nullable = false)
    private Long recipientId;

    @OneToOne(fetch = FetchType.LAZY) // One-to-one relationship with Users
    @JoinColumn(name = "USERID", nullable = false, unique = true)
    private Users user;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORITY", nullable = false)
    private Priority priority;

    @Column(name = "REQUESTEDUNITS", nullable = false)
    private Integer requestedUnits;

    public enum Priority {
        LOW, MEDIUM, HIGH;
        // Add a method for case-insensitive matching
        public static Priority fromString(String value) {
            return Priority.valueOf(value.toUpperCase());
        }
    }

    // Getters and Setters
    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Integer getRequestedUnits() {
        return requestedUnits;
    }

    public void setRequestedUnits(Integer requestedUnits) {
        this.requestedUnits = requestedUnits;
    }
}
