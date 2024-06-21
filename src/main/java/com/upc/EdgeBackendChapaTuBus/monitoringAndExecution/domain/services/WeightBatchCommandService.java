package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.WeightBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch.CreateWeightBatchCommand;

import java.util.Optional;

public interface WeightBatchCommandService {
    Optional<WeightBatch> handle(CreateWeightBatchCommand command);
}
