package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch;

public record ReceiveBusCapacityInformationCommand (
        Long unitBusId,
        String capacity
) {
}
