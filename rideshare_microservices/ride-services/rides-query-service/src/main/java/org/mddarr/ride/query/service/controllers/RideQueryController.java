package org.mddarr.ride.query.service.controllers;


import org.mddarr.ride.query.service.models.RideCoordinate;
import org.mddarr.ride.query.service.services.RideQueryServiceInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RideQueryController {

    private final RideQueryServiceInterface rideQueryService;

    public RideQueryController(RideQueryServiceInterface rideQueryService){
        this.rideQueryService = rideQueryService;
    }

    @GetMapping(value="rides/{rideID}")
    public List<RideCoordinate> getPatients(@PathVariable String rideID){
        return rideQueryService.getRideCoordinates(rideID);
    }


}
