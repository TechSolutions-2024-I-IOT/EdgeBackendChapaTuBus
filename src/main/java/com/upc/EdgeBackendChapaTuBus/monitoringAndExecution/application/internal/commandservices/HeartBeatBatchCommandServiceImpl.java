package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.application.internal.commandservices;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.HeartBeatBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.CreateHeartBeatBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.ReceiveHeartBeatPulseInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.SendHeartBeatAverageToCloudBackendCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.HeartBeatBatchCommandService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.LastTenPulsesService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa.HeartBeatBatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

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

/*
    @Override
    public Optional<HeartBeatPulse> handle(ReceiveHeartBeatPulseInformationCommand command) {

        Optional<HeartBeatBatch> heartBeatBatchOpt= heartBeatBatchRepository.findBySmartBandId(command.smartBandId());

        if(heartBeatBatchOpt.isEmpty())return Optional.empty();

        HeartBeatBatch heartBeatBatch = heartBeatBatchOpt.get();
        HeartBeatPulse newHeartBeatPulse= heartBeatBatch.receiveNewPulse(command);
        heartBeatBatchRepository.save(heartBeatBatch);
        return Optional.of(newHeartBeatPulse);
    }
*/
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

        //Actualizar esta función para mandarle la info al backend cloud
/*        // Crear un objeto de datos para enviar
        SensorData sensorData = new SensorData();
        sensorData.setPulse(String.valueOf(average));

        // Enviar al CloudBackend
        restTemplate.postForObject("https://chapatubusbackend.azurewebsites.net/api/v1/sensor-data", sensorData, SensorData.class);
        System.out.println("Sent average pulse to cloud backend.");*/
    }
    @Override
    public Optional<HeartBeatPulse> handle(SendHeartBeatAverageToCloudBackendCommand command) {
        return Optional.empty();
    }
}
