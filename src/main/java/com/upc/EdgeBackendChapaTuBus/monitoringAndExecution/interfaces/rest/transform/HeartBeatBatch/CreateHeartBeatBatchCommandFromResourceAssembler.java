package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.HeartBeatBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.CreateHeartBeatBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatBatch.CreateHeartBeatBatchResource;

public class CreateHeartBeatBatchCommandFromResourceAssembler {

    public static CreateHeartBeatBatchCommand toCommand(CreateHeartBeatBatchResource resource){
        return new CreateHeartBeatBatchCommand(
                resource.smartBandId()
        );
    }
}
