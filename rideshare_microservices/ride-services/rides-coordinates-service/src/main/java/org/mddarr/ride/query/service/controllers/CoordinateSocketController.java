package org.mddarr.ride.query.service.controllers;


import org.mddarr.ride.query.service.models.CoordinatesMessage;
import org.mddarr.ride.query.service.models.CoordinatesResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class CoordinateSocketController {

    @MessageMapping("/coordinates")
    @SendTo("/topic/coordinates")
    public CoordinatesResponse greeting(@Payload CoordinatesMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new CoordinatesResponse("Hello, " + message.getLat());
    }

}
