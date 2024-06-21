package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat;

public record ReceiveHeartBeatPulseInformationCommand(
        Long smartBandId,
        String pulse
) {
}
