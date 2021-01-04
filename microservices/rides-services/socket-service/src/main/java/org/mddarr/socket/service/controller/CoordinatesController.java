package org.mddarr.socket.service.controller;

import org.mddarr.socket.service.model.CoordinatesMessage;
import org.mddarr.socket.service.model.CoordinatesResponse;
import org.mddarr.socket.service.model.Ride;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class CoordinatesController {

//    @MessageMapping("/coordinates")
//    @SendTo("/topic/coordinates")
//    public CoordinatesMessage coords(@Payload CoordinatesMessage coordinatesMessage) throws Exception {
//        Thread.sleep(1000); // simulated delay
//        System.out.println("I GET CALLED AND MY COORDS ARE " + coordinatesMessage.getLat());
//        return new CoordinatesMessage(coordinatesMessage.getLat(), coordinatesMessage.getLng());
//    }


    @MessageMapping("/coordinates")
    @SendTo("/topic/coordinates")
    public CoordinatesResponse sendDriverCoordinates(@Payload CoordinatesMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay

        String rideid = message.getRideID();
        double lat = message.getLat();
        double lng = message.getLng();

        CoordinatesResponse coordinatesResponse = new CoordinatesResponse();

        return new CoordinatesResponse(rideid, lat, lng);
    }

}


