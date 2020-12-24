package org.mddarr.riderequestproducer.controllers;


import org.mddarr.riderequestproducer.models.RideRequest;
import org.mddarr.riderequestproducer.services.RideRequestAvroProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RideRequestsController {

    @Autowired
    RideRequestAvroProducer rideRequestAvroProducer;

    @PutMapping(value="riderequests")
    public String postRideRequest(@RequestBody RideRequest rideRequest){
        rideRequestAvroProducer.sendRideRequest(rideRequest);
        return "a";
    }

}

