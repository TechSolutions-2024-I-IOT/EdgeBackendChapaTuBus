package com.upc.EdgeBackendChapaTuBus.Rest;

import com.upc.EdgeBackendChapaTuBus.GPSSensor.Location;
import com.upc.EdgeBackendChapaTuBus.SensorData.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestSendDataToBackendCloudController {

    @Autowired
    private RestTemplate restTemplate;

    //private static final String CLOUD_BACKEND_SENSOR_DATA_URL = "http://localhost:8081/api/v1/sensor-data";
    //private static final String CLOUD_BACKEND_LOCATION_DATA_URL = "http://localhost:8081/api/v1/location-data";

    private static final String CLOUD_BACKEND_SENSOR_DATA_URL = "https://chapatubusbackend.azurewebsites.net/api/v1/sensor-data";
    private static final String CLOUD_BACKEND_LOCATION_DATA_URL = "https://chapatubusbackend.azurewebsites.net/api/v1/location-data";



    public void sendSensorData(SensorData sensorData){
        restTemplate.postForObject(CLOUD_BACKEND_SENSOR_DATA_URL,sensorData,SensorData.class);
    }

    public void sendLocationData(Location location){
        restTemplate.postForObject(CLOUD_BACKEND_LOCATION_DATA_URL,location,Location.class);
    }

}
