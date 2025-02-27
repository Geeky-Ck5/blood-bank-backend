package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointments, Long> {
    List<Appointments> findByDateAfter(LocalDate date);
    List<Appointments> findByDateBefore(LocalDate date);

    @Query("SELECT a FROM Appointments a WHERE a.userId = :userId AND a.date >= :today ORDER BY a.date ASC")
    List<Appointments> findUpcomingAppointmentsByUser(@Param("userId") Long userId, @Param("today") LocalDate today);
}

