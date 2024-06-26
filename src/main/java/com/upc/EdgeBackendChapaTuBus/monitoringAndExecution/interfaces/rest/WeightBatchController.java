package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest;


import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.WeightBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeCapacity;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.queries.GetAllCapacityForWeightSensorIdQuery;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.WeightBatchCommandService;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.WeightBatchQueryService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeCapacity.RealTimeCapacityReceivedResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.RealTimeCapacity.ReceiveRealTimeCapacityResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch.CreateWeightBatchResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch.WeightBatchCapacitiesResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch.WeightBatchCreated;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.RealTimeCapacity.RealTimeCapacityReceivedResourceFromEntityAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.RealTimeCapacity.ReceiveRealTimeCapacityCommandFromResourceAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.WeightBatch.CreateWeightBatchCommandFromResourceAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.WeightBatch.WeightBatchCapacitiesResourceFromEntityAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.WeightBatch.WeightBatchCreatedFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/weight-batch")
public class WeightBatchController {

    private final WeightBatchCommandService weightBatchCommandService;
    private final WeightBatchQueryService weightBatchQueryService;

    public WeightBatchController(WeightBatchCommandService weightBatchCommandService, WeightBatchQueryService weightBatchQueryService) {
        this.weightBatchCommandService = weightBatchCommandService;
        this.weightBatchQueryService = weightBatchQueryService;
    }

    @PostMapping("/new")
    ResponseEntity<WeightBatchCreated> createWeightBatch(@RequestBody CreateWeightBatchResource createWeightBatchResource){

        Optional<WeightBatch> weightBatch= weightBatchCommandService
                .handle(
                        CreateWeightBatchCommandFromResourceAssembler.toCommandFromResource(createWeightBatchResource)
                );

        return weightBatch.map(actualWeightBatch->
                        new ResponseEntity<>(WeightBatchCreatedFromEntityAssembler.toResourceFromEntity(actualWeightBatch),CREATED))
                .orElseGet(()->ResponseEntity.badRequest().build());

    }

    @PostMapping("/receive-occupied-seats")
    ResponseEntity<RealTimeCapacityReceivedResource> receiveRealTimeCapacity(@RequestBody ReceiveRealTimeCapacityResource resource){
        Optional<RealTimeCapacity> realTimeCapacity = weightBatchCommandService
                .handle(ReceiveRealTimeCapacityCommandFromResourceAssembler.toCommand(resource));

        return realTimeCapacity.map(actualRealTimeCapacity ->
                        new ResponseEntity<>(RealTimeCapacityReceivedResourceFromEntityAssembler.toResourceFromEntity(actualRealTimeCapacity), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/capacities/{weightSensorId}")
    ResponseEntity<WeightBatchCapacitiesResource> getAllCapacitiesForWeightSensorId(@PathVariable Long weightSensorId){
        List<RealTimeCapacity> capacities = weightBatchQueryService.handle(new GetAllCapacityForWeightSensorIdQuery(weightSensorId));

        if(capacities.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        WeightBatch weightBatch = capacities.get(0).getWeightBatch();
        WeightBatchCapacitiesResource resource = WeightBatchCapacitiesResourceFromEntityAssembler.toResourceFromEntityAssembler(weightBatch);
        return ResponseEntity.ok(resource);
    }
}
