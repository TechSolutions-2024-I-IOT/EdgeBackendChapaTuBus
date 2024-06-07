package com.upc.EdgeBackendChapaTuBus.GPSSensor;

import com.upc.EdgeBackendChapaTuBus.SensorData.SensorData;
import com.upc.EdgeBackendChapaTuBus.SensorData.SensorDataRepository;

public class LocationService {
    private final LocationRepository locationRepository;

    LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    private Location addLocationInformation(double latitude, double longitude){
        Location location= Location.builder()
                .lat(latitude)
                .lng(longitude)
                .build();

        return locationRepository.save(location);
    }
}
