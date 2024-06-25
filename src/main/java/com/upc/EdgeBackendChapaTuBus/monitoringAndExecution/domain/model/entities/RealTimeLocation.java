package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.LocationBatch;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RealTimeLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String latitude;
    private String longitude;
    private String speed;

    @ManyToOne
    @JoinColumn(name = "location_batch_id")
    private LocationBatch locationBatch;
}
