package com.panjuak.maintanance.repositories;

import com.panjuak.maintanance.entities.MaintenanceFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceFeatureRepository extends JpaRepository<MaintenanceFeature, String> {
}
