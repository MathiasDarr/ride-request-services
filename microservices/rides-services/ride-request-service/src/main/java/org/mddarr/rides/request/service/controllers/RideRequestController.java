package org.mddarr.rides.request.service.controllers;



import org.mddarr.rides.event.dto.AvroRideRequest;
import org.mddarr.rides.request.service.models.RideRequest;
import org.mddarr.rides.request.service.models.RideRequestResponse;
import org.mddarr.rides.request.service.services.AvroRideRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RideRequestController {

    @Autowired
    AvroRideRequestProducer avroRideRequestProducer;

    @PutMapping("rides/requests")
    @CrossOrigin(origins = "http://localhost:8091")
    public String postRideRequest(@RequestBody RideRequest rideRequest){
        return avroRideRequestProducer.sendRideRequest(rideRequest);
    }


}
