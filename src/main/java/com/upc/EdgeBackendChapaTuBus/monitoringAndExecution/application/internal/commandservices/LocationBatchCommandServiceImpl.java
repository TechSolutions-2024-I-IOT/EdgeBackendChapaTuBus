package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.application.internal.commandservices;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.LocationBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.CreateLocationBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.ReceiveBusLocationInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.SendBusLocationToCloudBackendCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeLocation;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.LocationBatchCommandService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa.LocationBatchRepository;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch.RegisterBusLocationLogResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class LocationBatchCommandServiceImpl implements LocationBatchCommandService {

    @Autowired
    private RestTemplate restTemplate;

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
        Optional<LocationBatch> locationBatchOpt = locationBatchRepository.findByUnitBusId(command.unitBusId());
        if (locationBatchOpt.isEmpty()) {
            System.out.println("No se encontró un LocationBatch con el unitBusId proporcionado.");
            return Optional.empty();
        }

        LocationBatch locationBatch = locationBatchOpt.get();
        String cloudBackendUrl = "https://chapatubusbackend.azurewebsites.net/api/v1/gps-tracker/register-bus-location-log";

        for (RealTimeLocation realTimeLocation : locationBatch.getRealTimeLocations()) {
            try {

                RegisterBusLocationLogResource resource = new RegisterBusLocationLogResource(
                        Math.toIntExact(locationBatch.getUnitBusId()),
                        realTimeLocation.getLatitude(),
                        realTimeLocation.getLongitude(),
                        realTimeLocation.getSpeed()
                );
                System.out.println("Enviando datos: " + resource);

                ResponseEntity<String> response = restTemplate.postForEntity(cloudBackendUrl, resource, String.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Datos enviados correctamente al backend.");
                } else {
                    System.out.println("Error al enviar datos al backend. Código de estado: " + response.getStatusCode());
                }
            } catch (Exception e) {
                System.out.println("Error al enviar datos al backend: " + e.getMessage());

            }
        }

        return Optional.empty();
    }
}
