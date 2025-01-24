package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments, Long> {
    @Query("SELECT a FROM Appointments a JOIN FETCH a.center c")
    List<Appointments> findAllAppointmentsWithCenters();
}
