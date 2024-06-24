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
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HeartBeatBatchCommandServiceImpl implements HeartBeatBatchCommandService {

    private final HeartBeatBatchRepository heartBeatBatchRepository;
    private final RestTemplate restTemplate;
    private final LastTenPulsesService lastTenPulsesService;
    private static final AtomicInteger currentId = new AtomicInteger(22);

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
        sendHeartBeatAverageToCloudBackend();
        lastTenPulsesService.clear();
    }

    return Optional.of(newHeartBeatPulse);
}

    private void sendHeartBeatAverageToCloudBackend() {
        int average = lastTenPulsesService.getLastTenPulses().stream()
                .mapToInt(pulse -> Integer.parseInt(pulse.getPulse()))
                .sum() / lastTenPulsesService.size();
        System.out.println("Calculated average pulse: " + average);

        Map<String, Object> sensorData = new HashMap<>();
        sensorData.put("id", currentId.incrementAndGet());
        sensorData.put("pulse", String.valueOf(average));

        String cloudBackendUrl = "https://chapatubusbackend.azurewebsites.net/api/v1/sensor-data";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(cloudBackendUrl, sensorData, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Sent average pulse to cloud backend successfully.");
            } else {
                System.out.println("Failed to send average pulse to cloud backend. Status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println("Error sending average pulse to cloud backend: " + e.getMessage());
        }
    }
    @Override
    public Optional<HeartBeatPulse> handle(SendHeartBeatAverageToCloudBackendCommand command) {
        return Optional.empty();
    }
}
