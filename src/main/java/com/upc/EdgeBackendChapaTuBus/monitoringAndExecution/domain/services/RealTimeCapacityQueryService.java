package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeCapacity;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.queries.GetAllCapacityForWeightSensorIdQuery;

import java.util.List;

public interface RealTimeCapacityQueryService {
    List<RealTimeCapacity> handle(GetAllCapacityForWeightSensorIdQuery query);
}
