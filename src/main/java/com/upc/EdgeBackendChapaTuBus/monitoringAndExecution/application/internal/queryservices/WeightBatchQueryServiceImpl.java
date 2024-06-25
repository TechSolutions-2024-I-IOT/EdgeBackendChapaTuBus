package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.application.internal.queryservices;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.WeightBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeCapacity;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.queries.GetAllCapacityForUnitBusIdQuery;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.WeightBatchQueryService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa.WeightBatchRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WeightBatchQueryServiceImpl implements WeightBatchQueryService {

    private final WeightBatchRepository weightBatchRepository;

    public WeightBatchQueryServiceImpl(WeightBatchRepository weightBatchRepository) {
        this.weightBatchRepository = weightBatchRepository;
    }

    @Override
    public List<RealTimeCapacity> handle(GetAllCapacityForUnitBusIdQuery query) {
        Optional<WeightBatch> weightBatchOpt = weightBatchRepository.findByUnitBusId(query.unitBusId());
        return weightBatchOpt.map(WeightBatch::getRealTimeCapacities).orElse(Collections.emptyList());
    }
}
