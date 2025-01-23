package com.bloodbank_webapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bloodbank_webapp.backend.model.BloodInventory;
import java.util.List;


@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, Long> {

    @Query("SELECT SUM(b.quantity) FROM BloodInventory b")
    Long totalBloodUnits();


    @Query("SELECT b.bloodType AS bloodGroup, SUM(b.quantity) AS unitsAvailable " +
            "FROM BloodInventory b GROUP BY b.bloodType")
    List<BloodGroupSummary> getBloodInventorySummary();

    interface BloodGroupSummary {
        String getBloodGroup();
        Long getUnitsAvailable();
    }

    @Query("SELECT b.bloodType, SUM(b.quantity), b.expiryDate " +
            "FROM BloodInventory b " +
            "GROUP BY b.bloodType, b.expiryDate " +
            "ORDER BY b.expiryDate ASC")
    List<Object[]> findBloodUnitsExpiringSoon();

}
