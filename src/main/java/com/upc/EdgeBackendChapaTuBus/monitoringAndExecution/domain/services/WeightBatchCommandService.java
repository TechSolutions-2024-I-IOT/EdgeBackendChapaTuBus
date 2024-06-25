package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.WeightBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch.CreateWeightBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch.ReceiveBusCapacityInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch.SendRealTimeCapacityToCloudBackendCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeCapacity;

import java.util.Optional;

public interface WeightBatchCommandService {
    Optional<WeightBatch> handle(CreateWeightBatchCommand command);
    Optional <RealTimeCapacity> handle(ReceiveBusCapacityInformationCommand command);
    Optional <SendRealTimeCapacityToCloudBackendCommand> handle(SendRealTimeCapacityToCloudBackendCommand command);
}
