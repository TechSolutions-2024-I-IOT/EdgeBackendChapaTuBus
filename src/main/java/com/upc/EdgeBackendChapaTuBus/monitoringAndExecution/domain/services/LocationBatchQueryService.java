package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeLocation;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.queries.GetAllLocationsForUnitBusIdQuery;

import java.util.List;

public interface LocationBatchQueryService {
    List<RealTimeLocation> handle(GetAllLocationsForUnitBusIdQuery query);
}
