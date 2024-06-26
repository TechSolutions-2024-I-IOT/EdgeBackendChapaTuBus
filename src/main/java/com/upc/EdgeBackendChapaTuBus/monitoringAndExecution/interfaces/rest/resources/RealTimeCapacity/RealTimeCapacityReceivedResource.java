package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeCapacity;

public record RealTimeCapacityReceivedResource (
        Long id,
        String capacity,
        int weightSensorId
){

}
