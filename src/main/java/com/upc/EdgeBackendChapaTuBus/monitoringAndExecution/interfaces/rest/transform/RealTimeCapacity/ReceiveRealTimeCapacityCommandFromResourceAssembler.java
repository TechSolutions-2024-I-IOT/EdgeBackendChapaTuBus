package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.RealTimeCapacity;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch.ReceiveBusCapacityInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeCapacity.ReceiveRealTimeCapacityResource;

public class ReceiveRealTimeCapacityCommandFromResourceAssembler {
    /*public static ReceiveHeartBeatPulseInformationCommand toCommand(ReceiveHeartBeatPulseResource resource){
        return new ReceiveHeartBeatPulseInformationCommand(
                (long) resource.smartBandId(),
                resource.pulse()
        );
    }*/
    public static ReceiveBusCapacityInformationCommand toCommand(ReceiveRealTimeCapacityResource resource){
        return new ReceiveBusCapacityInformationCommand(
                (long) resource.unitBusId(),
                resource.capacity()
        );
    }
}
