package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch;

public record ReceiveBusCapacityInformationCommand (
        Long weightSensorId,
        String capacity
) {
}
