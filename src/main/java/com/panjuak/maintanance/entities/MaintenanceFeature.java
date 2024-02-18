package com.panjuak.maintanance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "maintenance_items")
public class MaintenanceFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String feature;

    @Column(name = "isMaintenance", columnDefinition = "BOOLEAN")
    private Boolean isMaintenance;

    @ManyToOne
    @JoinColumn(name = "maintenance_id")
    @JsonIgnore
    private Maintenance maintenance;
}
