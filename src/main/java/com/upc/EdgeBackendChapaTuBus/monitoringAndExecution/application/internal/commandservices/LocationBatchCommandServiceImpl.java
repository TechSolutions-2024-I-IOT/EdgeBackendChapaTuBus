package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.application.internal.commandservices;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.LocationBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.CreateLocationBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.ReceiveBusLocationInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.SendBusLocationToCloudBackendCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch.CreateWeightBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeLocation;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.LocationBatchCommandService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa.LocationBatchRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationBatchCommandServiceImpl implements LocationBatchCommandService {

    private final LocationBatchRepository locationBatchRepository;

    public LocationBatchCommandServiceImpl(LocationBatchRepository locationBatchRepository) {
        this.locationBatchRepository = locationBatchRepository;
    }

    @Override
    public Optional<LocationBatch> handle(CreateLocationBatchCommand command) {
        LocationBatch locationBatch= new LocationBatch(command);
        return Optional.of(locationBatchRepository.save(locationBatch));
    }

    @Override
    public Optional<RealTimeLocation> handle(ReceiveBusLocationInformationCommand command) {

        Optional<LocationBatch> locationBatchOptional= locationBatchRepository.findByUnitBusId(command.unitBusId());

        if(locationBatchOptional.isEmpty())return Optional.empty();

        LocationBatch locationBatch = locationBatchOptional.get();
        RealTimeLocation newRealTimeLocation= locationBatch.receiveNewLocation(command);
        locationBatchRepository.save(locationBatch);
        return Optional.of(newRealTimeLocation);
    }

    @Override
    public Optional<RealTimeLocation> handle(SendBusLocationToCloudBackendCommand command) {
        return Optional.empty();
    }
}
