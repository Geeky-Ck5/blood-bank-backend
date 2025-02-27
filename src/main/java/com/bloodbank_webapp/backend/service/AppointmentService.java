package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.AppointmentDTO;
import com.bloodbank_webapp.backend.model.Appointments;
import com.bloodbank_webapp.backend.repository.AppointmentRepository;
import com.bloodbank_webapp.backend.repository.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentsRepository;
    @Autowired
    private CenterRepository centerRepository;

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentsRepository.findAll().stream()
                .map(AppointmentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public AppointmentDTO scheduleAppointment(AppointmentDTO appointmentsDTO) {
        Appointments appointment = appointmentsDTO.toEntity(centerRepository);
        Appointments savedAppointment = appointmentsRepository.save(appointment);
        return AppointmentDTO.fromEntity(savedAppointment);
    }

    public List<AppointmentDTO> getUpcomingAppointments() {
        LocalDate today = LocalDate.now();
        return appointmentsRepository.findByDateAfter(today).stream()
                .map(AppointmentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getPastAppointments() {
        LocalDate today = LocalDate.now();
        return appointmentsRepository.findByDateBefore(today).stream()
                .map(AppointmentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void cancelAppointment(Long appointmentId) {
        appointmentsRepository.deleteById(appointmentId);
    }

    public void updateAppointmentStatus(Long appointmentId, String status) {
        Appointments appointment = appointmentsRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(Appointments.AppointmentStatus.fromValue(status));
        appointmentsRepository.save(appointment);
    }

    // Get upcoming appointments by User ID
    public List<AppointmentDTO> getUpcomingAppointmentsByUser(Long userId) {
        LocalDate today = LocalDate.now();
        List<Appointments> appointments = appointmentsRepository.findUpcomingAppointmentsByUser(userId, today);
        return appointments.stream()
                .map(AppointmentDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
