package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest;


import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.WeightBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.WeightBatchCommandService;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch.CreateWeightBatchResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.WeightBatch.WeightBatchCreated;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.WeightBatch.CreateWeightBatchCommandFromResourceAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.WeightBatch.WeightBatchCreatedFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/weight-batch")
public class WeightBatchController {

    private final WeightBatchCommandService weightBatchCommandService;


    public WeightBatchController(WeightBatchCommandService weightBatchCommandService) {
        this.weightBatchCommandService = weightBatchCommandService;
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
}
