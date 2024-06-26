package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.LocationBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.SendBusLocationToCloudBackendCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeLocation;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.queries.GetAllLocationsForUnitBusIdQuery;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.LocationBatchCommandService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.LocationBatchQueryService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch.CreateLocationBatchResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch.LocationBatchCreated;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch.LocationBatchResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.LocationBatch.*;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeLocation.RealTimeLocationReceivedResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeLocation.ReceiveRealTimeLocationResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.LocationBatch.CreateLocationBatchCommandFromResourceAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.LocationBatch.LocationBatchCreatedFromEntityAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.LocationBatch.LocationBatchResourceFromEntityAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.LocationBatch.*;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.RealTimeLocation.RealTimeLocationReceivedResourceFromEntityAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.RealTimeLocation.ReceiveRealTimeLocationCommandFromResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/location-batch")
public class LocationBatchController {

    private final LocationBatchCommandService locationBatchCommandService;
    private final LocationBatchQueryService locationBatchQueryService;


    public LocationBatchController(
            LocationBatchCommandService locationBatchCommandService,
            LocationBatchQueryService locationBatchQueryService
            ) {
        this.locationBatchCommandService = locationBatchCommandService;
        this.locationBatchQueryService = locationBatchQueryService;
    }

    @PostMapping("/new")
    ResponseEntity<LocationBatchCreated> createLocationBatch(@RequestBody CreateLocationBatchResource createLocationBatchResource){

        Optional<LocationBatch> locationBatch = locationBatchCommandService
                .handle(
                        CreateLocationBatchCommandFromResourceAssembler.toCommandFromResource(createLocationBatchResource)
                );

        return locationBatch.map(actualLocationBatch->
                new ResponseEntity<>(LocationBatchCreatedFromEntityAssembler.toResourceFromEntity(actualLocationBatch), CREATED))
                .orElseGet(()->ResponseEntity.badRequest().build());

    }

    @PostMapping("/receive-bus-location")
    ResponseEntity<RealTimeLocationReceivedResource> receiveRealTimeCapacity(@RequestBody ReceiveRealTimeLocationResource resource){
        Optional<RealTimeLocation> realTimeLocation = locationBatchCommandService.handle(ReceiveRealTimeLocationCommandFromResourceAssembler.toCommand(resource));
        return realTimeLocation.map(actualRealTimeLocation->
                new ResponseEntity<>(RealTimeLocationReceivedResourceFromEntityAssembler.toResourceFromEntity(actualRealTimeLocation), CREATED))
                .orElseGet(()->ResponseEntity.badRequest().build());

    }

    @GetMapping("/get-location/{unitBusId}")
    public ResponseEntity<LocationBatchResource> getAllLocationBatch(@PathVariable Long unitBusId){
        List<RealTimeLocation> realTimeLocations = locationBatchQueryService.handle(new GetAllLocationsForUnitBusIdQuery(unitBusId));

        if (realTimeLocations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        LocationBatch locationBatch = realTimeLocations.get(0).getLocationBatch();
        LocationBatchResource resource = LocationBatchResourceFromEntityAssembler.toResourceFromEntityAssembler(locationBatch);

        return ResponseEntity.ok(resource);
    }

    @PostMapping("/send-location/{unitBusId}")
    ResponseEntity<Void> sendBusLocationToCloudBackend(@PathVariable Long unitBusId) {
        locationBatchCommandService.handle(new SendBusLocationToCloudBackendCommand(unitBusId));
        return ResponseEntity.ok().build();
    }
}
