package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeCapacity;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.queries.GetAllCapacityForUnitBusIdQuery;

import java.util.List;

public interface WeightBatchQueryService {
    List<RealTimeCapacity> handle(GetAllCapacityForUnitBusIdQuery query);
}