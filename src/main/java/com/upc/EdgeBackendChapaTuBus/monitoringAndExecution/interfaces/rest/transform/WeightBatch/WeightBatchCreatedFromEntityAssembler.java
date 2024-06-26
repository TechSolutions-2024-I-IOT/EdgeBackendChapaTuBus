package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.WeightBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.WeightBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch.WeightBatchCreated;

public class WeightBatchCreatedFromEntityAssembler {
    public static WeightBatchCreated toResourceFromEntity(WeightBatch entity) {
        return new WeightBatchCreated(
                entity.getId(),
                entity.getWeightSensorId()
        );
    }
}
