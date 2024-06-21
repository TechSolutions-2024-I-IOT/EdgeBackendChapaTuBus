package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatBatch.DTO.HeartBeatPulseDto;

import java.util.List;

public record HeartBeatBatchPulsesResource
        (
                Long heartBeatBatchId,
                Long smartBandId,
                List<HeartBeatPulseDto> heartBeatPulses
        )
{
}
