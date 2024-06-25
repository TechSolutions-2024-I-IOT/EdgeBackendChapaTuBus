package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeLocation;

public record ReceiveRealTimeLocationResource(
        int unitBusId,
        String latitude,
        String longitude
) {
}
