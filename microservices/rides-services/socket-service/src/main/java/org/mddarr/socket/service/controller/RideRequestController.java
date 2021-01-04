package org.mddarr.socket.service.controller;


import org.mddarr.rides.event.dto.AvroRideRequest;
import org.mddarr.socket.service.model.requests.RideRequest;

import org.mddarr.socket.service.model.responses.RideRequestResponse;
import org.mddarr.socket.service.services.AvroRideRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
public class RideRequestController {

    @Autowired
    private SimpMessagingTemplate template;

//    @PutMapping("rides/request")

    @Autowired
    private AvroRideRequestProducer avroRideRequestProducer;


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

    @MessageMapping("/rides/requests/post")
    @SendTo("/topic/rides/requests/post")
    public String sendRideRequest(RideRequest rideRequest) throws Exception {
        Thread.sleep(1000); // simulated delay
        //System.out.println("string " + rideRequest);
        String requestid = avroRideRequestProducer.sendRideRequest(rideRequest);
//        return rideRequest;
        return requestid;
    }
//

}
