package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatPulse;

public record ReceiveHeartBeatPulseResource
        (int heartBeatBatchId,
         String pulse
         )
{
}
