package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch;
public record RegisterBusLocationLogResource(
        int GpsTrackerId,
        String latitude,
        String longitude,
        String speed
) {
}
