package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.HeartBeatBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.CreateHeartBeatBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.ReceiveHeartBeatPulseInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.SendHeartBeatAverageToCloudBackendCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;

import java.util.Optional;

public interface HeartBeatBatchCommandService {
    Optional<HeartBeatBatch> handle(CreateHeartBeatBatchCommand command);
    Optional<HeartBeatPulse> handle(ReceiveHeartBeatPulseInformationCommand command);
    Optional<HeartBeatPulse> handle(SendHeartBeatAverageToCloudBackendCommand command);
}
