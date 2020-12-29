package org.mddarr.ride.query.service.controllers;


import org.mddarr.ride.query.service.models.RideCoordinate;
import org.mddarr.ride.query.service.services.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RideCoordinatesController {

    private final LocationService locationService;

    public RideCoordinatesController(LocationService locationService){
        this.locationService = locationService;
    }

    @GetMapping(value="rides/{rideID}")
    public List<RideCoordinate> getCoordinates(@PathVariable String rideID ){
        return locationService.getRideCoordinates(rideID);
    }


}
