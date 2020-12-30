package org.mddarr.ride.coordinate.service.controllers;



import org.mddarr.ride.coordinate.service.models.RideRequest;
import org.mddarr.ride.coordinate.service.services.AvroRideCoordinatesProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RideRequestController {

    @Autowired
    AvroRideCoordinatesProducer avroRideCoordinatesProducer;

    @PutMapping
    public String postRideRequest(@RequestBody RideRequest rideRequest){
        avroRideCoordinatesProducer.sendRideRequest(rideRequest);
        return "dfd";
    }

}
