package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch.DTO.RealTimeLocationDto;

import java.util.List;

public record LocationBatchResource (
        Long LocationBatchId,
        Long unitBusId,
        List<RealTimeLocationDto> realTimeLocations
) {
}
