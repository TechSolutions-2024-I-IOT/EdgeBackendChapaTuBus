package com.upc.EdgeBackendChapaTuBus.Rest;

import com.upc.EdgeBackendChapaTuBus.SensorData.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestSendDataToBackendCloudController {

    @Autowired
    private RestTemplate restTemplate;

    //private static final String CLOUD_BACKEND_URL = "http://localhost:8081/api/v1/sensor-data";
    private static final String CLOUD_BACKEND_URL = "https://sensordatacloudbackend.azurewebsites.net/api/v1/sensor-data";

    public void sendSensorData(SensorData sensorData){
        restTemplate.postForObject(CLOUD_BACKEND_URL,sensorData,SensorData.class);
    }

}
