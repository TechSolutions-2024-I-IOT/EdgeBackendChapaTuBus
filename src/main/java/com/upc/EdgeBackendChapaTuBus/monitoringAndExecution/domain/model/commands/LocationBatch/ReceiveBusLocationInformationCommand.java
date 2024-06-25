package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch;

public record ReceiveBusLocationInformationCommand (
        Long unitBusId,
        String latitude,
        String longitude
) {
}
