package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.CreateLocationBatchCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.commands.LocationBatch.ReceiveBusLocationInformationCommand;
import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.RealTimeLocation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "location_batch")
public class LocationBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long unitBusId;

    @OneToMany(mappedBy = "locationBatch",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RealTimeLocation> realTimeLocations;

    public LocationBatch() {
        this.unitBusId = null;
        this.realTimeLocations = new ArrayList<>();
    }

    public LocationBatch(CreateLocationBatchCommand command) {
        this.unitBusId = command.unitBusId();
        this.realTimeLocations = new ArrayList<>();
    }

    public RealTimeLocation receiveNewLocation(ReceiveBusLocationInformationCommand command){
        RealTimeLocation realTimeLocation = RealTimeLocation.builder()
                .locationBatch(this)
                .latitude(command.latitude())
                .longitude(command.longitude())
                .build();
        this.realTimeLocations.add(realTimeLocation);
        return realTimeLocation;
    }


}
