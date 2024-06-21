package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.HeartBeatBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.queries.GetAllPulsesForSmartBandIdQuery;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.HeartBeatBatchCommandService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.HeartBeatBatchQueryService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatBatch.CreateHeartBeatBatchResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatBatch.HeartBeatBatchCreated;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatBatch.HeartBeatBatchPulsesResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatPulse.HeartBeatPulseReceivedResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.resources.HeartBeatPulse.ReceiveHeartBeatPulseResource;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.HeartBeatBatch.CreateHeartBeatBatchCommandFromResourceAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.HeartBeatBatch.HeartBeatBatchPulsesResourceFromEntityAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.HeartBeatBatch.HeartBeatchBatchCreatedFromEntityAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.HeartBeatPulse.HeartBeatPulseReceivedResourceFromEntityAssembler;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest.transform.HeartBeatPulse.ReceiveHeartBeatPulseInformationCommandFromResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/heart-beat-batch")
public class HeartBeatBatchController {

    private final HeartBeatBatchCommandService heartBeatBatchCommandService;
    private final HeartBeatBatchQueryService heartBeatBatchQueryService;

    HeartBeatBatchController(
            HeartBeatBatchCommandService heartBeatBatchCommandService,
            HeartBeatBatchQueryService heartBeatBatchQueryService
    ){
        this.heartBeatBatchCommandService= heartBeatBatchCommandService;
        this.heartBeatBatchQueryService = heartBeatBatchQueryService;
    }

    @PostMapping("/new")
    ResponseEntity<HeartBeatBatchCreated> createHeartBeatBatch(@RequestBody CreateHeartBeatBatchResource createHeartBeatBatchResource){

        Optional<HeartBeatBatch> heartBeatBatch= heartBeatBatchCommandService
                .handle(
                        CreateHeartBeatBatchCommandFromResourceAssembler.toCommand(createHeartBeatBatchResource)
                );

        return heartBeatBatch.map(actualHeartBeatBatch->
                new ResponseEntity<>(HeartBeatchBatchCreatedFromEntityAssembler.toResourceFromEntity(actualHeartBeatBatch),CREATED))
                .orElseGet(()->ResponseEntity.badRequest().build());

    }

    @PostMapping("/receive-heart-beat-pulse")
    ResponseEntity<HeartBeatPulseReceivedResource> receiveHeartBeatPulse(@RequestBody ReceiveHeartBeatPulseResource resource){

        Optional<HeartBeatPulse> heartBeatPulse= heartBeatBatchCommandService
                .handle(ReceiveHeartBeatPulseInformationCommandFromResourceAssembler.toCommand(resource));

        return heartBeatPulse.map(actualHeartBeatPulse->
                new ResponseEntity<>(HeartBeatPulseReceivedResourceFromEntityAssembler.toResourceFromEntity(actualHeartBeatPulse),CREATED))
                .orElseGet(()->ResponseEntity.badRequest().build());
    }

    @GetMapping("/pulses/{smartBandId}")
    ResponseEntity<HeartBeatBatchPulsesResource> getAllPulsesForSmartBandId(@PathVariable Long smartBandId) {
        List<HeartBeatPulse> pulses = heartBeatBatchQueryService.handle(new GetAllPulsesForSmartBandIdQuery(smartBandId));

        if (pulses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        HeartBeatBatch heartBeatBatch = pulses.get(0).getHeartBeatBatch();
        HeartBeatBatchPulsesResource resource = HeartBeatBatchPulsesResourceFromEntityAssembler.toResourceFromEntityAssembler(heartBeatBatch);

        return ResponseEntity.ok(resource);
    }
}
