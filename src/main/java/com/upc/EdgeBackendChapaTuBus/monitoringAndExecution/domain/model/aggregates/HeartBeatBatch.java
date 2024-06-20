package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.CreateHeartBeatBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.ReceiveHeartBeatPulseInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

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

    private String name;

    @OneToMany(mappedBy = "heartBeatBatch",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<HeartBeatPulse> heartBeatPulses;

    public HeartBeatBatch() {
        this.name= Strings.EMPTY;
        this.heartBeatPulses = new ArrayList<>();
    }

    public HeartBeatBatch(CreateHeartBeatBatchCommand command){
        this.name=command.name();
    }

    public HeartBeatPulse receiveNewPulse(ReceiveHeartBeatPulseInformationCommand command){
        HeartBeatPulse heartBeatPulse= HeartBeatPulse.builder()
                .heartBeatBatch(this)
                .pulse(command.pulse())
                .build();

        this.heartBeatPulses.add(heartBeatPulse);
        return heartBeatPulse;
    }

}
