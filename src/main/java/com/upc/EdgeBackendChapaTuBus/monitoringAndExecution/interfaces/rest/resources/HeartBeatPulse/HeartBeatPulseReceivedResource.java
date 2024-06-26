package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatPulse;

public record HeartBeatPulseReceivedResource(
        Long id,
        int smartBandId,
        String pulse
) {
}
