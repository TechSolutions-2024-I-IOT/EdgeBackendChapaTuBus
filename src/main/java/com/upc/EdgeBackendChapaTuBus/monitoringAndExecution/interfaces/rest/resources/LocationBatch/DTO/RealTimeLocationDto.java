package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch.DTO;

public record RealTimeLocationDto(
        Long id,
        String latitude,
        String longitude,
        String speed
) {
}
