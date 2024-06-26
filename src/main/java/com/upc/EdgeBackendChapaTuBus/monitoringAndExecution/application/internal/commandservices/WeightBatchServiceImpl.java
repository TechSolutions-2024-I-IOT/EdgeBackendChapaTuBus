package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.application.internal.commandservices;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.HeartBeatBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.WeightBatch;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.CreateHeartBeatBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch.CreateWeightBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch.ReceiveBusCapacityInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch.SendRealTimeCapacityToCloudBackendCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeCapacity;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services.WeightBatchCommandService;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.infraestructure.repositories.jpa.WeightBatchRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeightBatchServiceImpl implements WeightBatchCommandService {

    private final WeightBatchRepository weightBatchRepository;

    public WeightBatchServiceImpl(WeightBatchRepository weightBatchRepository) {
        this.weightBatchRepository = weightBatchRepository;
    }

    @Override
    public Optional<WeightBatch> handle(CreateWeightBatchCommand command) {

        WeightBatch weightBatch= new WeightBatch(command);
        return Optional.of(weightBatchRepository.save(weightBatch));
    }
    @Override
    public Optional<RealTimeCapacity> handle(ReceiveBusCapacityInformationCommand command){
        Optional<WeightBatch> weightBatchOpt = weightBatchRepository.findByWeightSensorId(command.weightSensorId());
        if(weightBatchOpt.isEmpty()) return Optional.empty();

        WeightBatch weightBatch = weightBatchOpt.get();
        RealTimeCapacity newRealTimeCapacity = weightBatch.receiveNewCapacity(command);
        weightBatchRepository.save(weightBatch);
        return Optional.of(newRealTimeCapacity);
    }

    @Override
    public Optional<RealTimeCapacity> handle(SendRealTimeCapacityToCloudBackendCommand command) {
        return Optional.empty();
    }
}
