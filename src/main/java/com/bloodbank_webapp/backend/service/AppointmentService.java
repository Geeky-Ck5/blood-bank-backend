package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.AppointmentDTO;
import com.bloodbank_webapp.backend.model.Appointments;
import com.bloodbank_webapp.backend.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<AppointmentDTO> getAllAppointments() {
        List<Appointments> appointments = appointmentRepository.findAllAppointmentsWithCenters();

        // Map entity to DTO
        List<AppointmentDTO> appointmentDTOs = new ArrayList<>();
        for (Appointments appointment : appointments) {
            AppointmentDTO dto = new AppointmentDTO();
            dto.setAppointmentId(appointment.getAppointmentId());
            dto.setUserId(appointment.getUserId());
            dto.setCenterName(appointment.getCenter().getName()); // Fetch center name
            dto.setDate(appointment.getDate());
            dto.setTime(appointment.getTime());
            dto.setStatus(appointment.getStatus().toString());
            appointmentDTOs.add(dto);
        }

        return appointmentDTOs;
    }
}