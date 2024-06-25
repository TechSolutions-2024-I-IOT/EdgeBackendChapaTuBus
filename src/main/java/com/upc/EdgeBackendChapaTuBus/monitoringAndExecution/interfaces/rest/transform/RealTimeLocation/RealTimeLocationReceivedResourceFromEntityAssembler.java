package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.RealTimeLocation;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeLocation;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeLocation.RealTimeLocationReceivedResource;

public class RealTimeLocationReceivedResourceFromEntityAssembler {
    public static RealTimeLocationReceivedResource toResourceFromEntity(RealTimeLocation entity) {
        return new RealTimeLocationReceivedResource(
                entity.getId(),
                entity.getLatitude(),
                entity.getLongitude(),
                Math.toIntExact(entity.getLocationBatch().getId())
        );
    }
}
