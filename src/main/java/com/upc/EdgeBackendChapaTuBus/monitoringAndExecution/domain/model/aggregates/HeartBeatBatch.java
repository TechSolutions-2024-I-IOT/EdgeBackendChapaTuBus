package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.CreateHeartBeatBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.ReceiveHeartBeatPulseInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "heart_beat_batch")
public class HeartBeatBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long smartBandId;

    @OneToMany(mappedBy = "heartBeatBatch",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<HeartBeatPulse> heartBeatPulses;

    public HeartBeatBatch() {
        this.smartBandId = null;
        this.heartBeatPulses = new ArrayList<>();
    }

    public HeartBeatBatch(CreateHeartBeatBatchCommand command) {
        this.smartBandId = command.smartBandId();
        this.heartBeatPulses = new ArrayList<>();
    }

    public HeartBeatPulse receiveNewPulse(ReceiveHeartBeatPulseInformationCommand command) {
        HeartBeatPulse heartBeatPulse = HeartBeatPulse.builder()
                .heartBeatBatch(this)
                .pulse(command.pulse())
                .build();

        this.heartBeatPulses.add(heartBeatPulse);
        return heartBeatPulse;
    }

}
