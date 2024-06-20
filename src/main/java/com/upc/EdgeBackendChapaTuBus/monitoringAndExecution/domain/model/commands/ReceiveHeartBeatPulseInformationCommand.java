package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands;

public record ReceiveHeartBeatPulseInformationCommand(
        Long HeartBeatBatchId,
        String pulse
) {
}
