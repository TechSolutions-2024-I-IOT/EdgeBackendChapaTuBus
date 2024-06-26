package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.WeightBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.WeightBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch.DTO.RealTimeCapacityDto;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch.WeightBatchCapacitiesResource;

import java.util.List;
import java.util.stream.Collectors;

public class WeightBatchCapacitiesResourceFromEntityAssembler {
    public static WeightBatchCapacitiesResource toResourceFromEntityAssembler(WeightBatch weightBatch){
        List<RealTimeCapacityDto> capacityDtos = weightBatch.getRealTimeCapacities().stream()
                .map(realTimeCapacity -> new RealTimeCapacityDto(realTimeCapacity.getId(), realTimeCapacity.getCapacity()))
                .collect(Collectors.toList());

        return new WeightBatchCapacitiesResource(
                weightBatch.getId(),
                weightBatch.getWeightSensorId(),
                capacityDtos
        );
    }
}
