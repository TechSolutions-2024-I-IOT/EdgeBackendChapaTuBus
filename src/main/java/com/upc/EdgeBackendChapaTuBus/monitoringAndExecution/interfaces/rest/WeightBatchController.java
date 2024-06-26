package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.interfaces.rest;


import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.WeightBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeCapacity;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.queries.GetAllCapacityForWeightSensorIdQuery;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.WeightBatchCommandService;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.WeightBatchQueryService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa.WeightBatchRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/weight-batch")
public class WeightBatchController {

    private final WeightBatchCommandService weightBatchCommandService;
    private final WeightBatchQueryService weightBatchQueryService;
    private final WeightBatchRepository weightBatchRepository;
    private final RestTemplate restTemplate;

    public WeightBatchController(WeightBatchCommandService weightBatchCommandService, WeightBatchQueryService weightBatchQueryService, WeightBatchRepository weightBatchRepository, RestTemplate restTemplate) {
        this.weightBatchCommandService = weightBatchCommandService;
        this.weightBatchQueryService = weightBatchQueryService;
        this.weightBatchRepository = weightBatchRepository;
        this.restTemplate = restTemplate;
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
    ResponseEntity<RealTimeCapacityReceivedResource> receiveRealTimeCapacity(@RequestBody ReceiveRealTimeCapacityResource resource) {
        Optional<RealTimeCapacity> realTimeCapacity = weightBatchCommandService
                .handle(ReceiveRealTimeCapacityCommandFromResourceAssembler.toCommand(resource));

        realTimeCapacity.ifPresent(capacity -> {
            WeightBatch weightBatch = capacity.getWeightBatch();
            List<RealTimeCapacity> capacities = weightBatch.getRealTimeCapacities();

            // Comparar la capacidad anterior con la nueva
            if (capacities.size() == 1 || !capacities.get(capacities.size() - 2).getCapacity().equals(capacity.getCapacity())) {
                sendRealTimeCapacityToCloudBackend(weightBatch.getId());
            }
        });

        return realTimeCapacity.map(actualRealTimeCapacity ->
                        new ResponseEntity<>(RealTimeCapacityReceivedResourceFromEntityAssembler.toResourceFromEntity(actualRealTimeCapacity), HttpStatus.CREATED))
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

    private void sendRealTimeCapacityToCloudBackend(Long weightBatchId) {
        WeightBatch weightBatch = weightBatchRepository.findById(weightBatchId)
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar el WeightBatch actual"));

        System.out.println("WeightSensorId recuperado: " + weightBatch.getWeightSensorId());
        System.out.println("Iniciando envío de datos al cloud backend. WeightBatchId recibido: " + weightBatchId);

        // Obtener la capacidad más reciente
        RealTimeCapacity latestCapacity = weightBatch.getRealTimeCapacities().stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new RuntimeException("No se encontraron capacidades para este WeightBatch"));

        System.out.println("Capacidad actual: " + latestCapacity.getCapacity());

        Map<String, Object> sensorData = new LinkedHashMap<>();
        sensorData.put("weightSensorId", weightBatch.getWeightSensorId());
        sensorData.put("busCapacity", Integer.parseInt(latestCapacity.getCapacity()));
        System.out.println("Datos a enviar: " + sensorData);

        String cloudBackendUrl = "https://chapatubusbackend.azurewebsites.net/api/v1/weight-sensor/register-bus-capacity";

        try {
            System.out.println("Intentando enviar datos a: " + cloudBackendUrl);
            ResponseEntity<String> response = restTemplate.postForEntity(cloudBackendUrl, sensorData, String.class);
            System.out.println("Respuesta recibida. Código de estado: " + response.getStatusCodeValue());
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Sent real-time capacity to cloud backend successfully. WeightSensorId: " + weightBatch.getWeightSensorId());
                System.out.println("Respuesta del servidor: " + response.getBody());
            } else {
                System.out.println("Failed to send real-time capacity to cloud backend. Status code: " + response.getStatusCode());
                System.out.println("Respuesta del servidor: " + response.getBody());
            }
        } catch (Exception e) {
            System.out.println("Error sending real-time capacity to cloud backend: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
