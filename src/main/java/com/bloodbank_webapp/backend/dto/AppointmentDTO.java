package com.bloodbank_webapp.backend.dto;

import com.bloodbank_webapp.backend.model.Appointments;
import com.bloodbank_webapp.backend.model.Center;
import com.bloodbank_webapp.backend.repository.CenterRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDTO {
    private Long appointmentId;
    private Long userId;
    private String centerName; // ✅ Storing center name instead of ID
    private LocalDate date;
    private LocalTime time;
    private String status;
    private String cancellationReason;

    // Constructors
    public AppointmentDTO() {}

    public AppointmentDTO(Long appointmentId, Long userId, String centerName, LocalDate date, LocalTime time, String status, String cancellationReason) {
        this.appointmentId = appointmentId;
        this.userId = userId;
        this.centerName = centerName;
        this.date = date;
        this.time = time;
        this.status = status;
        this.cancellationReason = cancellationReason;
    }

    // Convert DTO to Entity
    public Appointments toEntity(CenterRepository centerRepository) {
        Appointments appointment = new Appointments();
        appointment.setAppointmentId(this.appointmentId);
        appointment.setUserId(this.userId);

        if (this.centerName == null || this.centerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Center name cannot be null or empty");
        }

        Center center = centerRepository.findByName(this.centerName)
                .orElseThrow(() -> new IllegalArgumentException("Center not found: " + this.centerName));

        appointment.setCenter(center);
        appointment.setDate(this.date);
        appointment.setTime(this.time);

        if (this.status == null) {
            appointment.setStatus(Appointments.AppointmentStatus.scheduled); // ✅ Default to 'scheduled'
        } else {
            appointment.setStatus(Appointments.AppointmentStatus.fromValue(this.status));
        }

        appointment.setCancellationReason(this.cancellationReason); // ✅ Set cancellation reason

        return appointment;
    }

    // Convert Entity to DTO
    public static AppointmentDTO fromEntity(Appointments appointment) {
        return new AppointmentDTO(
                appointment.getAppointmentId(),
                appointment.getUserId(),
                appointment.getCenter().getName(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getStatus().toString(),
                appointment.getCancellationReason()
        );
    }

    // Getters & Setters
    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
}
