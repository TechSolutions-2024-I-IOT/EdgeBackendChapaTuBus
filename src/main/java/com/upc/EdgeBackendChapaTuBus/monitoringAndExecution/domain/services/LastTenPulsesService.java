package com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.services;


import com.upc.EdgeBackendChapaTuBus.monitoringAndExecution.domain.model.entities.HeartBeatPulse;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class LastTenPulsesService {

    private final LinkedList<HeartBeatPulse> lastTenPulses = new LinkedList<>();

    public void addPulse(HeartBeatPulse pulse) {
        if (lastTenPulses.size() >= 10) {
            lastTenPulses.removeFirst(); // Eliminar el pulso más antiguo
        }
        lastTenPulses.add(pulse);
    }

    public List<HeartBeatPulse> getLastTenPulses() {
        return new LinkedList<>(lastTenPulses);
    }

    public void clear() {
        lastTenPulses.clear();
    }

    public int size() {
        return lastTenPulses.size();
    }
}