package org.mddarr.location_tracker.controller;

import org.mddarr.location_tracker.model.CoordinatesMessage;
import org.mddarr.location_tracker.model.CoordinatesResponse;
import org.mddarr.location_tracker.model.Greeting;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;


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
    public CoordinatesResponse greeting(@Payload CoordinatesMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new CoordinatesResponse("Hello, " + message.getLat());
    }

}


