package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.LocationBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.CreateLocationBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.ReceiveBusLocationInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.SendBusLocationToCloudBackendCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeLocation;

import java.util.Optional;

public interface LocationBatchCommandService {
    Optional<LocationBatch> handle(CreateLocationBatchCommand command);
    Optional<RealTimeLocation> handle(ReceiveBusLocationInformationCommand command);
    Optional<RealTimeLocation> handle(SendBusLocationToCloudBackendCommand command);
}
