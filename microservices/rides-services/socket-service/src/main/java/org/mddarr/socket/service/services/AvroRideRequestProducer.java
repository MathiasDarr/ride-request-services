package org.mddarr.socket.service.services;

import org.mddarr.rides.event.dto.AvroRideRequest;


import org.mddarr.socket.service.Constants;
import org.mddarr.socket.service.model.requests.RideRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AvroRideRequestProducer implements AvroRideRequestInterface{

    @Autowired
    private KafkaTemplate<String, AvroRideRequest> kafkaTemplateEvent1;

    private static final Logger logger = LoggerFactory.getLogger(AvroRideRequestProducer.class);

    @Override
    public String sendRideRequest(RideRequest rideRequest) {
        String request_id = UUID.randomUUID().toString();
        AvroRideRequest avroRideRequest = AvroRideRequest.newBuilder()
                .setRequestId(request_id)
                .setUserId(rideRequest.getUserid())
                .setCity(rideRequest.getCity())
                .setDestination(rideRequest.getDestination())
                .setRiders(rideRequest.getRiders()).build();
        System.out.println("Send ride request  {}" +  rideRequest.getDestination());
        logger.info("Send ride request  {}", avroRideRequest.getCity());
        kafkaTemplateEvent1.send(Constants.RIDE_REQUEST_TOPIC, avroRideRequest);
        return request_id;
    }

}
