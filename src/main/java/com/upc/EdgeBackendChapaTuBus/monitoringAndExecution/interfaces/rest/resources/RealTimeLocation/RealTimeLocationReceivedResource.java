package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeLocation;

public record RealTimeLocationReceivedResource(
        Long id,
        String latitude,
        String longitude,
        String speed,
        int unitBusId
) {
}
