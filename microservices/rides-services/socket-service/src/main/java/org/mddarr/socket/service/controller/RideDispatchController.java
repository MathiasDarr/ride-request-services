package org.mddarr.socket.service.controller;

import org.mddarr.socket.service.model.Ride;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class RideDispatchController {

    @MessageMapping("/rides")
    @SendTo("/topic/rides")
    public Ride sendMatchedRide() throws Exception {
        Thread.sleep(1000); // simulated delay
        Ride ride = new Ride("ride1", "driver1", "user1");
        return ride;
    }

}
