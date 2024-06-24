package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeCapacity;

public record ReceiveRealTimeCapacityResource (
        int unitBusId,
        String capacity
){
}
