package com.panjuak.maintanance.repositories;

import com.panjuak.maintanance.entities.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, String> {
}
