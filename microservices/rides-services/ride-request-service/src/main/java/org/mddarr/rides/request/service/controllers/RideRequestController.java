package org.mddarr.rides.request.service.controllers;



import org.mddarr.rides.request.service.models.RideRequest;
import org.mddarr.rides.request.service.services.AvroRideRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RideRequestController {

    @Autowired
    AvroRideRequestProducer avroRideRequestProducer;

    @PutMapping("rides/requests")
    @CrossOrigin(origins = "http://localhost:8090")
    public String postRideRequest(@RequestBody RideRequest rideRequest){
        return avroRideRequestProducer.sendRideRequest(rideRequest);
    }

}
