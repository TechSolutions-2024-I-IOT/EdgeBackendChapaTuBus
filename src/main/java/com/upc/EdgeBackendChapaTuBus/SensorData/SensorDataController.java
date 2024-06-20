package com.upc.EdgeBackendChapaTuBus.SensorData;

import com.upc.EdgeBackendChapaTuBus.Rest.RestSendDataToBackendCloudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/")
public class SensorDataController {

    private SensorDataService sensorDataService;
    private SensorDataRepository sensorDataRepository;
    private final RestSendDataToBackendCloudController restSendDataToBackendCloudController;

    private List<Integer> pulseData= new ArrayList<>();
    private int pulseCount=0;
    //Tamaño del lote de heartbeats recibidos
    private static final int BATCH_SIZE=10;

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
    public ResponseEntity<SensorData> createHeartBeat(@RequestBody SensorData sensorData) {

        int pulse= Integer.parseInt(sensorData.getPulse());
        pulseData.add(pulse);
        pulseCount++;

        if(pulseCount == BATCH_SIZE){
            //Calcular el promedio
            int sum=0;
            for(int p: pulseData){
                sum+=p;
            }

            int averagePulse= sum/BATCH_SIZE;

            //Crear un nuevo SensorData con el promedio
            SensorData averageSensorData = new SensorData();
            averageSensorData.setPulse(String.valueOf(averagePulse));

            //Guardar el promedio en el repositorio
            SensorData savedSensorData = sensorDataRepository.save(averageSensorData);

            //Enviar el promedio al backend cloud
            restSendDataToBackendCloudController.sendSensorData(savedSensorData);

            //Limpiar la lista y el contador
            pulseData.clear();
            pulseCount=0;

            return ResponseEntity.ok(savedSensorData);
        }

        //Guardar el pulso original en el repositorio
        SensorData savedSensorData = sensorDataRepository.save(sensorData);
        return ResponseEntity.ok(savedSensorData);
    }

}
