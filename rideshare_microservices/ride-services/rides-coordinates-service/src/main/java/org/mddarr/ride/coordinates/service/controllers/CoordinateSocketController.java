package org.mddarr.ride.coordinates.service.controllers;


import org.mddarr.ride.coordinates.service.models.CoordinatesMessage;
import org.mddarr.ride.coordinates.service.models.CoordinatesResponse;
import org.mddarr.ride.coordinates.service.services.CoordinatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class CoordinateSocketController {

    @Autowired
    CoordinatesService coordinatesService;


    @MessageMapping("/coordinates")
    @SendTo("/topic/coordinates")
    public CoordinatesResponse greeting(@Payload CoordinatesMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        coordinatesService.postCoordinatesData(message);
        return new CoordinatesResponse("Hello, " + message.getLatitude());
    }

}
