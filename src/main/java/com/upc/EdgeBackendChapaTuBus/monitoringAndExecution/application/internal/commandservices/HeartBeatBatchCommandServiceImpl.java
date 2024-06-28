package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.application.internal.commandservices;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.HeartBeatBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.CreateHeartBeatBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.ReceiveHeartBeatPulseInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.SendHeartBeatAverageToCloudBackendCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.HeartBeatBatchCommandService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.LastTenPulsesService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa.HeartBeatBatchRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HeartBeatBatchCommandServiceImpl implements HeartBeatBatchCommandService {

    private final HeartBeatBatchRepository heartBeatBatchRepository;
    private final RestTemplate restTemplate;
    private final LastTenPulsesService lastTenPulsesService;
    public HeartBeatBatchCommandServiceImpl(HeartBeatBatchRepository heartBeatBatchRepository, RestTemplate restTemplate, LastTenPulsesService lastTenPulsesService) {
        this.heartBeatBatchRepository = heartBeatBatchRepository;
        this.restTemplate = restTemplate;
        this.lastTenPulsesService = lastTenPulsesService;
    }


    @Override
    public Optional<HeartBeatBatch> handle(CreateHeartBeatBatchCommand command) {

        HeartBeatBatch heartBeatBatch= new HeartBeatBatch(command);
        heartBeatBatchRepository.save(heartBeatBatch);

        return Optional.of(heartBeatBatch);
    }
@Override
public Optional<HeartBeatPulse> handle(ReceiveHeartBeatPulseInformationCommand command) {
    Optional<HeartBeatBatch> heartBeatBatchOpt = heartBeatBatchRepository.findBySmartBandId(command.smartBandId());

    if (heartBeatBatchOpt.isEmpty()) return Optional.empty();

    HeartBeatBatch heartBeatBatch = heartBeatBatchOpt.get();
    HeartBeatPulse newHeartBeatPulse = heartBeatBatch.receiveNewPulse(command);
    heartBeatBatchRepository.save(heartBeatBatch);

    lastTenPulsesService.addPulse(newHeartBeatPulse);

    if (lastTenPulsesService.size() == 10) {
        System.out.println("lastTenPulses size is 10. Sending average to cloud backend.");
        sendHeartBeatAverageToCloudBackend(command.smartBandId());
        lastTenPulsesService.clear();
    }

    return Optional.of(newHeartBeatPulse);
}

    private void sendHeartBeatAverageToCloudBackend(Long smartBandId) {

        //Long smartBandId = heartBeatBatchRepository.findById(id)
        //        .map(HeartBeatBatch::getSmartBandId)
        //        .orElseThrow(() -> new RuntimeException("No se pudo encontrar el SmartBandId actual"));
        //System.out.println("SmartBandId recuperado: " + smartBandId);

        System.out.println("Iniciando envío de datos al cloud backend. ID recibido: " + smartBandId);
        int average = lastTenPulsesService.getLastTenPulses().stream()
                .mapToInt(pulse -> Integer.parseInt(pulse.getPulse()))
                .sum() / lastTenPulsesService.size();
        System.out.println("Calculated average pulse: " + average);
        System.out.println("Número de pulsos utilizados para el cálculo: " + lastTenPulsesService.size());

        Map<String, Object> sensorData = new LinkedHashMap<>();
        sensorData.put("SmartBandId", smartBandId);
        sensorData.put("heartRate", average);
        System.out.println("Datos a enviar: " + sensorData);

        String cloudBackendUrl = "https://chapatubusbackend.azurewebsites.net/api/v1/smart-band/register-heart-rate-log";

        try {
            System.out.println("Intentando enviar datos a: " + cloudBackendUrl);
            ResponseEntity<String> response = restTemplate.postForEntity(cloudBackendUrl, sensorData, String.class);
            System.out.println("Respuesta recibida. Código de estado: " + response.getStatusCodeValue());
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Sent average pulse to cloud backend successfully. SmartBandId: " + smartBandId);
                System.out.println("Respuesta del servidor: " + response.getBody());
            } else {
                System.out.println("Failed to send average pulse to cloud backend. Status code: " + response.getStatusCode());
                System.out.println("Respuesta del servidor: " + response.getBody());
            }
        } catch (Exception e) {
            System.out.println("Error sending average pulse to cloud backend: " + e.getMessage());
            e.printStackTrace(); // Esto imprimirá el stack trace completo
        }
    }
    @Override
    public Optional<HeartBeatPulse> handle(SendHeartBeatAverageToCloudBackendCommand command) {
        return Optional.empty();
    }
}
