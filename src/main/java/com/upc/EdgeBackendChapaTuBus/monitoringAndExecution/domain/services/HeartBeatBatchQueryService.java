package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.queries.GetAllPulsesForSmartBandIdQuery;

import java.util.List;

public interface HeartBeatBatchQueryService {
    List<HeartBeatPulse> handle(GetAllPulsesForSmartBandIdQuery query);
}
