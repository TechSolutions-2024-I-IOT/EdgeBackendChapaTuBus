package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.application.internal.queryservices;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.HeartBeatBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.queries.GetAllPulsesForSmartBandIdQuery;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.HeartBeatBatchQueryService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa.HeartBeatBatchRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class HeartBeatBatchQueryServiceImpl implements HeartBeatBatchQueryService {

    private final HeartBeatBatchRepository heartBeatBatchRepository;

    public HeartBeatBatchQueryServiceImpl(HeartBeatBatchRepository heartBeatBatchRepository) {
        this.heartBeatBatchRepository = heartBeatBatchRepository;
    }

    @Override
    public List<HeartBeatPulse> handle(GetAllPulsesForSmartBandIdQuery query) {
        Optional<HeartBeatBatch> heartBeatBatchOpt = heartBeatBatchRepository.findBySmartBandId(query.smartBandId());
        return heartBeatBatchOpt.map(HeartBeatBatch::getHeartBeatPulses).orElse(Collections.emptyList());
    }
}
