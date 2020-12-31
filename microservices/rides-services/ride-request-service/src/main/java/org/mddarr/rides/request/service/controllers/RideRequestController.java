package org.mddarr.rides.request.service.controllers;



import org.mddarr.rides.request.service.models.RideRequest;
import org.mddarr.rides.request.service.services.AvroRideRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RideRequestController {

    @Autowired
    AvroRideRequestProducer avroRideRequestProducer;

    @PutMapping("rides/requests")
    public String postRideRequest(@RequestBody RideRequest rideRequest){
        return avroRideRequestProducer.sendRideRequest(rideRequest);
    }

}
