package com.panjuak.maintanance.repositories;

import com.panjuak.maintanance.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, String> {

    @Query("SELECT m FROM Maintenance m WHERE "
            + "((m.startTime <= CURRENT_TIMESTAMP AND m.endTime >= CURRENT_TIMESTAMP) AND m.status = 'schedule') "
            + "OR m.status = 'process'")
    Optional<Maintenance> findFirstForTodayOrActive();

    List<Maintenance> findByStatusAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(String status, LocalDateTime startTime, LocalDateTime endTime);
}
