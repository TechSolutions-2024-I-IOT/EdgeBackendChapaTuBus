package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.LocationBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.LocationBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch.LocationBatchCreated;

public class LocationBatchCreatedFromEntityAssembler {
    public static LocationBatchCreated toResourceFromEntity(LocationBatch entity) {
        return new LocationBatchCreated(
                entity.getId(),
                entity.getUnitBusId()
        );
    }
}
