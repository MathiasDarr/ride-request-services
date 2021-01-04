package org.mddarr.rides.request.service.controllers;

import org.mddarr.rides.event.dto.AvroRideRequest;
import org.mddarr.rides.request.service.models.RideRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class RideRequestSocketController {

    @MessageMapping("/rides/requests/alert")
    @SendTo("/topic/rides/requests/alert")
    public RideRequestResponse sendMessage(@Payload AvroRideRequest rideRequest) {
//        System.out.println( rideRequest. +  " HIT WITH A RIDE REQUEST FROM " + rideRequest.getRequestId() + " TO THE DESTIONATION OF  " + rideRequest.getUserId()) ;
        RideRequestResponse rideRequestResponse = new RideRequestResponse();
        rideRequestResponse.setDestination("charleston");
        rideRequestResponse.setRiders(rideRequest.getRiders());
        rideRequestResponse.setRideid(rideRequest.getRequestId());
        return rideRequestResponse;
    }


}
