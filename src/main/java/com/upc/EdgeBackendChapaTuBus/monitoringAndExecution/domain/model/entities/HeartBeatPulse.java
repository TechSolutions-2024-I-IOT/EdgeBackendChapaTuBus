package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities;

import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.aggregates.HeartBeatBatch;
import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Strings;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HeartBeatPulse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String pulse;

    @ManyToOne
    @JoinColumn(name = "heart_beat_batch_id")
    private HeartBeatBatch heartBeatBatch;

}
