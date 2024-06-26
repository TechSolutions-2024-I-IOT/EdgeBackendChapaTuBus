package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeCapacity;

public record ReceiveRealTimeCapacityResource (
        int weightSensorId,
        String capacity
){
}
