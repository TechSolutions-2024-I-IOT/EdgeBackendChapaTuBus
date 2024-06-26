package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.HeartBeatPulse;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatPulse.HeartBeatPulseReceivedResource;

public class HeartBeatPulseReceivedResourceFromEntityAssembler {
    public static HeartBeatPulseReceivedResource toResourceFromEntity(HeartBeatPulse entity){
        return new HeartBeatPulseReceivedResource(
                entity.getId(),
                Math.toIntExact(entity.getHeartBeatBatch().getId()),
                entity.getPulse()
        );
    }
}
