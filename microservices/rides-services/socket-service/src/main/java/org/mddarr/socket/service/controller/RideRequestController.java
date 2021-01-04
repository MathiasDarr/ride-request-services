package org.mddarr.socket.service.controller;


import org.mddarr.socket.service.model.Ride;
import org.mddarr.socket.service.model.RideRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class RideRequestController {

    @MessageMapping("/rides/requests")
    @SendTo("/topic/rides/requests")
    public String sendMatchedRide(String rideRequest) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("string " + rideRequest);
//        System.out.println("I GET HIT WITH A RIDE REQUEST FROM " + rideRequest.getUserid() + " TO THE DESTIONATION OF  " + rideRequest.getDestination()) ;
//        return rideRequest;
        return rideRequest;
    }


}
