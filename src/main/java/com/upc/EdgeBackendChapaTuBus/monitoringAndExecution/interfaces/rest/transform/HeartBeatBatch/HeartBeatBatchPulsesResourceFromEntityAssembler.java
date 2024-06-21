package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.HeartBeatBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.HeartBeatBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatBatch.DTO.HeartBeatPulseDto;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatBatch.HeartBeatBatchPulsesResource;

import java.util.List;
import java.util.stream.Collectors;

public class HeartBeatBatchPulsesResourceFromEntityAssembler {

    public static HeartBeatBatchPulsesResource toResourceFromEntityAssembler(HeartBeatBatch heartBeatBatch) {
        List<HeartBeatPulseDto> pulseDtos = heartBeatBatch.getHeartBeatPulses().stream()
                .map(pulse -> new HeartBeatPulseDto(pulse.getId(), pulse.getPulse()))
                .collect(Collectors.toList());

        return new HeartBeatBatchPulsesResource(
                heartBeatBatch.getId(),
                heartBeatBatch.getSmartBandId(),
                pulseDtos
        );
    }
}