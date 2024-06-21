package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.WeightBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch.CreateWeightBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch.CreateWeightBatchResource;

public class CreateWeightBatchCommandFromResourceAssembler {

    public static CreateWeightBatchCommand toCommandFromResource(CreateWeightBatchResource resource) {
        return new CreateWeightBatchCommand(
                resource.unitBusId()
        );
    }
}
