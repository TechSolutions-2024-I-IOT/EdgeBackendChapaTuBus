package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.WeightBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.WeightBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeCapacity;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch.DTO.RealTimeCapacityDto;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch.WeightBatchCapacityResource;

import java.util.List;
import java.util.stream.Collectors;

public class WeightBatchCapacityResourceFromEntityAssembler {

    public static WeightBatchCapacityResource toResourceFromEntityAssembler(WeightBatch weightBatch) {
        List<RealTimeCapacityDto> realTimeCapacityDtos = weightBatch.getRealTimeCapacities().stream()
                .map(realTimeCapacity -> new RealTimeCapacityDto(realTimeCapacity.getId(), realTimeCapacity.getCapacity()))
                .collect(Collectors.toList());

        return new WeightBatchCapacityResource(
                weightBatch.getId(),
                weightBatch.getUnitBusId(),
                realTimeCapacityDtos
        );
    }
}

