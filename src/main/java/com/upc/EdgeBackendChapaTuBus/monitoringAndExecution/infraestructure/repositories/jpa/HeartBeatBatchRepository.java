package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.HeartBeatBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface HeartBeatBatchRepository extends JpaRepository<HeartBeatBatch,Long> {
    Optional<HeartBeatBatch> findBySmartBandId(Long smartBandId);
}
