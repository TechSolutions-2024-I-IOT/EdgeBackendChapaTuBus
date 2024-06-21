package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.WeightBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightBatchRepository extends JpaRepository<WeightBatch, Long> {

}
