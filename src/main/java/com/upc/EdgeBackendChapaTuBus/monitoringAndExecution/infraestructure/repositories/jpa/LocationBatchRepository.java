package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.LocationBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationBatchRepository extends JpaRepository<LocationBatch, Long> {
    Optional<LocationBatch> findByUnitBusId(Long unitBusId);
}
