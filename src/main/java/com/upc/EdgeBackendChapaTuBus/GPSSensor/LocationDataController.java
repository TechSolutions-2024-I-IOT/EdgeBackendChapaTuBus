package com.upc.EdgeBackendChapaTuBus.GPSSensor;

import com.upc.EdgeBackendChapaTuBus.Rest.RestSendDataToBackendCloudController;
import com.upc.EdgeBackendChapaTuBus.SensorData.SensorData;
import com.upc.EdgeBackendChapaTuBus.SensorData.SensorDataRepository;
import com.upc.EdgeBackendChapaTuBus.SensorData.SensorDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/")
public class LocationDataController {

    private LocationRepository locationRepository;
    private final RestSendDataToBackendCloudController restSendDataToBackendCloudController;

    LocationDataController(
            LocationRepository locationRepository
            , RestSendDataToBackendCloudController restSendDataToBackendCloudController){
        this.locationRepository=locationRepository  ;
        this.restSendDataToBackendCloudController = restSendDataToBackendCloudController;
    }

    @GetMapping("location-data")
    public ResponseEntity<List<Location>> getAllLocationData() {
        List<Location> locations = (List<Location>) locationRepository.findAll();
        return ResponseEntity.ok(locations);
    }

    @PostMapping("location-data")
    public ResponseEntity<Location> createLocationData(@RequestBody Location location) {
        Location savedLocationData = locationRepository.save(location);
        restSendDataToBackendCloudController.sendLocationData(savedLocationData);
        return ResponseEntity.ok(savedLocationData);
    }

}
