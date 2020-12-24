package org.mddarr.rideservice.controllers;



import org.mddarr.rideservice.models.requests.PostRideRequest;
import org.mddarr.rideservice.services.AvroRideRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RideRequestController {

    @Autowired
    AvroRideRequestProducer avroRideRequestProducer;

    @PutMapping
    public String postRideRequest(@RequestBody PostRideRequest rideRequest){
        avroRideRequestProducer.sendRideRequest(rideRequest);
        return "dfd";
    }

}
