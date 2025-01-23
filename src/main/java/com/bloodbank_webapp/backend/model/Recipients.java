package com.bloodbank_webapp.backend.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "RECIPIENTS")
public class  Recipients {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "RECIPIENTID", nullable = false)
        private Long recipientId;

        @Column(name = "FULLNAME", nullable = false, length = 100)
        private String fullName;

        public Long getRecipientId() {
            return recipientId;
        }

        public void setRecipientId(Long recipientId) {
            this.recipientId = recipientId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public LocalDateTime getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(LocalDateTime dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public String getBloodGroup() {
            return bloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        @Column(name = "DATEOFBIRTH", nullable = false)
        private LocalDateTime dateOfBirth;

        @Enumerated(EnumType.STRING)
        @Column(name = "GENDER", nullable = false)
        private Gender gender;

        @Column(name = "BLOODGROUP",nullable = true, length = 3)
        private String bloodGroup; // E.g., A+, B-, etc.

        @Column(name = "CONTACTNUMBER", nullable = false, length = 15)
        private String contactNumber;

        @Column(name = "EMAIL", length = 100, unique = true)
        private String email;

        @Column(name = "ADDRESS", length = 255)
        private String address;

        @Column(name = "CREATED_AT", updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();

        @Column(name = "STATUS", length = 50)
        private String status = "Pending";

        @Column(name = "PRIORITY", length = 50)
        private String priority = "Medium";

        public enum Gender {
            Male, Female, Other
        }
}

