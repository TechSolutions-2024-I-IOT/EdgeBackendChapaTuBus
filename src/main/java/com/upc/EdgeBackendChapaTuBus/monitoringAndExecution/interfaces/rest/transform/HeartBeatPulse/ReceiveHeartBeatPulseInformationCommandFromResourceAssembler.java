package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.HeartBeatPulse;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.ReceiveHeartBeatPulseInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatPulse.ReceiveHeartBeatPulseResource;

public class ReceiveHeartBeatPulseInformationCommandFromResourceAssembler {
    public static ReceiveHeartBeatPulseInformationCommand toCommand(ReceiveHeartBeatPulseResource resource){
        return new ReceiveHeartBeatPulseInformationCommand(
                (long) resource.smartBandId(),
                resource.pulse()
        );
    }
}
