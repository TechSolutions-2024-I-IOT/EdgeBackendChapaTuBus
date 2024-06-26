package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch.DTO.RealTimeCapacityDto;

import java.util.List;

public record WeightBatchCapacitiesResource (
        Long weightBatchId,
        Long WeightSensorId,
        List<RealTimeCapacityDto> realTimeCapacities
) {
}
