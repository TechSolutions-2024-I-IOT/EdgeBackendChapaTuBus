package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.LocationBatch;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.LocationBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch.DTO.RealTimeLocationDto;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch.LocationBatchCreated;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch.LocationBatchResource;

import java.util.List;
import java.util.stream.Collectors;

public class LocationBatchResourceFromEntityAssembler {

    public static LocationBatchResource toResourceFromEntityAssembler(LocationBatch locationBatch) {
        List<RealTimeLocationDto> realTimeLocationDtos = locationBatch.getRealTimeLocations().stream()
                .map(realTimeLocation -> new RealTimeLocationDto(
                        realTimeLocation.getId(),
                        realTimeLocation.getLatitude(),
                        realTimeLocation.getLongitude()
                ))
                .collect(Collectors.toList());

        return new LocationBatchResource(
                locationBatch.getId(),
                locationBatch.getUnitBusId(),
                realTimeLocationDtos

        );
    }
}
