package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.application.internal.commandservices;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.HeartBeatBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.CreateHeartBeatBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.ReceiveHeartBeatPulseInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.SendHeartBeatAverageToCloudBackendCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.HeartBeatBatchCommandService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa.HeartBeatBatchRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HeartBeatBatchCommandServiceImpl implements HeartBeatBatchCommandService {

    private final HeartBeatBatchRepository heartBeatBatchRepository;

    public HeartBeatBatchCommandServiceImpl(HeartBeatBatchRepository heartBeatBatchRepository) {
        this.heartBeatBatchRepository = heartBeatBatchRepository;
    }


    @Override
    public Optional<HeartBeatBatch> handle(CreateHeartBeatBatchCommand command) {

        HeartBeatBatch heartBeatBatch= new HeartBeatBatch(command);
        heartBeatBatchRepository.save(heartBeatBatch);

        return Optional.of(heartBeatBatch);
    }

    @Override
    public Optional<HeartBeatPulse> handle(ReceiveHeartBeatPulseInformationCommand command) {

        Optional<HeartBeatBatch> heartBeatBatchOpt= heartBeatBatchRepository.findById(command.HeartBeatBatchId());

        if(heartBeatBatchOpt.isEmpty())return Optional.empty();

        HeartBeatBatch heartBeatBatch = heartBeatBatchOpt.get();
        HeartBeatPulse newHeartBeatPulse= heartBeatBatch.receiveNewPulse(command);
        heartBeatBatchRepository.save(heartBeatBatch);
        return Optional.of(newHeartBeatPulse);
    }

    @Override
    public Optional<HeartBeatPulse> handle(SendHeartBeatAverageToCloudBackendCommand command) {
        return Optional.empty();
    }
}
