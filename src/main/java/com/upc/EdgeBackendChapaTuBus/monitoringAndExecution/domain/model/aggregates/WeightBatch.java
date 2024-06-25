package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.HeartBeat.CreateHeartBeatBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.WeightBatch.CreateWeightBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeCapacity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "weight_batch")
public class WeightBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long unitBusId;

    @OneToMany(mappedBy = "weightBatch",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RealTimeCapacity> realTimeCapacities;

    public WeightBatch() {
        this.unitBusId = null;
        this.realTimeCapacities = new ArrayList<>();
    }

    public WeightBatch(CreateWeightBatchCommand command) {
        this.unitBusId = command.unitBusId();
        this.realTimeCapacities = new ArrayList<>();
    }

}
