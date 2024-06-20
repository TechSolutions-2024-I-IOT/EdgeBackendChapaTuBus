package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.HeartBeatBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface HeartBeatBatchRepository extends JpaRepository<HeartBeatBatch,Long> {
}
