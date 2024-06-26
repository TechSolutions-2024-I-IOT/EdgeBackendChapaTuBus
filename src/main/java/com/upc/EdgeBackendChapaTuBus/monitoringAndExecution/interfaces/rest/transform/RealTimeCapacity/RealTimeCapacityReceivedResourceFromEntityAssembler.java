package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.RealTimeCapacity;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeCapacity;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeCapacity.RealTimeCapacityReceivedResource;

public class RealTimeCapacityReceivedResourceFromEntityAssembler {
    public static RealTimeCapacityReceivedResource toResourceFromEntity(RealTimeCapacity entity){
        return new RealTimeCapacityReceivedResource(
                entity.getId(),
                entity.getCapacity(),
                Math.toIntExact(entity.getWeightBatch().getId())
        );
    }
}
