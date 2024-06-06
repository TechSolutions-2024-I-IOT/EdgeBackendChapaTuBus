package com.upc.EdgeBackendChapaTuBus.SensorData;

import com.upc.EdgeBackendChapaTuBus.Rest.RestSendDataToBackendCloudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/")
public class SensorDataController {

    private SensorDataService sensorDataService;
    private SensorDataRepository sensorDataRepository;
    private final RestSendDataToBackendCloudController restSendDataToBackendCloudController;

    SensorDataController(
            SensorDataService sensorDataService,
            SensorDataRepository sensorDataRepository, RestSendDataToBackendCloudController restSendDataToBackendCloudController){
        this.sensorDataRepository=sensorDataRepository;
        this.sensorDataService=sensorDataService;
        this.restSendDataToBackendCloudController = restSendDataToBackendCloudController;
    }

    @GetMapping("sensor-data")
    public ResponseEntity<List<SensorData>> getAllSensorData() {
        List<SensorData> sensorDatas = (List<SensorData>) sensorDataRepository.findAll();
        return ResponseEntity.ok(sensorDatas);
    }

    @PostMapping("sensor-data")
    public ResponseEntity<SensorData> createTask(@RequestBody SensorData sensorData) {
        SensorData savedSensorData = sensorDataRepository.save(sensorData);
        restSendDataToBackendCloudController.sendSensorData(savedSensorData);
        return ResponseEntity.ok(savedSensorData);
    }

}
