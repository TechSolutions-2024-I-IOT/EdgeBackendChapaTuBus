package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.application.internal.queryservices;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.LocationBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeLocation;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.queries.GetAllLocationsForUnitBusIdQuery;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.LocationBatchQueryService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa.LocationBatchRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LocationBatchQueryServiceImpl implements LocationBatchQueryService {

    private final LocationBatchRepository locationBatchRepository;

    public LocationBatchQueryServiceImpl(LocationBatchRepository locationBatchRepository) {
        this.locationBatchRepository = locationBatchRepository;
    }

    @Override
    public List<RealTimeLocation> handle(GetAllLocationsForUnitBusIdQuery query) {
        Optional<LocationBatch> locationBatchOpt = locationBatchRepository.findByUnitBusId(query.unitBusId());
        return locationBatchOpt.map(LocationBatch::getRealTimeLocations).orElse(Collections.emptyList());
    }
}
