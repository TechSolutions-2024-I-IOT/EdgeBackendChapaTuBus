package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.HeartBeatBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.HeartBeatBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatBatch.HeartBeatBatchCreated;

public class HeartBeatchBatchCreatedFromEntityAssembler {

    public static HeartBeatBatchCreated toResourceFromEntity(HeartBeatBatch entity){
        return new HeartBeatBatchCreated(
                entity.getId(),
                entity.getSmartBandId()
        );
    }
}
