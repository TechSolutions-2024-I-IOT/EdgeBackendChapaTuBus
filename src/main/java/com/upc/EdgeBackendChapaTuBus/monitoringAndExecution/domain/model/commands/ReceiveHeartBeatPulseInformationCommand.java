package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands;

public record ReceiveHeartBeatPulseInformationCommand(
        Long smartBandId,
        String pulse
) {
}
