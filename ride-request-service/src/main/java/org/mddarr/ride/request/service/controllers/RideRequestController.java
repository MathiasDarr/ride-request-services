package org.mddarr.ride.request.service.controllers;



import org.mddarr.ride.request.service.models.requests.PostRideRequest;
import org.mddarr.ride.request.service.services.RideRequestService;
import org.mddarr.ride.request.service.services.AvroRideRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RideRequestController {

    @Autowired
    AvroRideRequestProducer avroRideRequestProducer;

    @Autowired
    RideRequestService rideRequestService;

    @PutMapping
    public String postRideRequest(@RequestBody PostRideRequest rideRequest){
        String request_id = rideRequestService.postRideRequest(rideRequest);
        avroRideRequestProducer.sendRideRequest(rideRequest);
        return request_id;
    }

}
