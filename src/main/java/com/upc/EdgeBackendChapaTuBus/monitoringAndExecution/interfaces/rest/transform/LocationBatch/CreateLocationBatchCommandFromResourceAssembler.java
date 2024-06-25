package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.LocationBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.CreateLocationBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch.CreateLocationBatchResource;

public class CreateLocationBatchCommandFromResourceAssembler {

    public static CreateLocationBatchCommand toCommandFromResource(CreateLocationBatchResource resource) {
        return new CreateLocationBatchCommand(
                resource.unitBusId()
        );
    }
}
