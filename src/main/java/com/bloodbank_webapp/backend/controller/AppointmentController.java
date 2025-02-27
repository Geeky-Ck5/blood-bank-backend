package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.dto.AppointmentDTO;
import com.bloodbank_webapp.backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentsService;

    // Get all appointments
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentsService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Schedule a new appointment
    @PostMapping("/schedule")
    public ResponseEntity<AppointmentDTO> scheduleAppointment(@RequestBody AppointmentDTO appointmentsDTO) {
        AppointmentDTO newAppointment = appointmentsService.scheduleAppointment(appointmentsDTO);
        return ResponseEntity.ok(newAppointment);
    }

    // Get upcoming appointments
    @GetMapping("/upcoming")
    public ResponseEntity<List<AppointmentDTO>> getUpcomingAppointments() {
        List<AppointmentDTO> upcomingAppointments = appointmentsService.getUpcomingAppointments();
        return ResponseEntity.ok(upcomingAppointments);
    }

    // Get past appointments
    @GetMapping("/past")
    public ResponseEntity<List<AppointmentDTO>> getPastAppointments() {
        List<AppointmentDTO> pastAppointments = appointmentsService.getPastAppointments();
        return ResponseEntity.ok(pastAppointments);
    }

    // Cancel an appointment
    @DeleteMapping("/cancel/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId) {
        appointmentsService.cancelAppointment(appointmentId);
        return ResponseEntity.ok("Appointment canceled successfully.");
    }

    // Update appointment status
    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<String> updateAppointmentStatus(@PathVariable Long appointmentId, @RequestBody String status) {
        appointmentsService.updateAppointmentStatus(appointmentId, status);
        return ResponseEntity.ok("Appointment status updated successfully.");
    }

    @GetMapping("/upcoming/{userId}")
    public ResponseEntity<List<AppointmentDTO>> getUpcomingAppointmentsByUser(@PathVariable Long userId) {
        List<AppointmentDTO> appointments = appointmentsService.getUpcomingAppointmentsByUser(userId);
        return ResponseEntity.ok(appointments);
    }
}
