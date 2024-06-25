package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.RealTimeLocation;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.ReceiveBusLocationInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeLocation.ReceiveRealTimeLocationResource;

public class ReceiveRealTimeLocationCommandFromResourceAssembler {
    public static ReceiveBusLocationInformationCommand toCommand(ReceiveRealTimeLocationResource resource) {
        return new ReceiveBusLocationInformationCommand(
                (long) resource.unitBusId(),
                resource.latitude(),
                resource.longitude()
        );
    }
}
