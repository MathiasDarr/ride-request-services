package org.mddarr.rideservice.services;
import org.mddarr.rides.event.dto.AvroRideRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;



@Service
public class AvroRideRequestProducer implements AvroRideRequestInterface {

    @Autowired
    private KafkaTemplate<String, AvroRideRequest> kafkaTemplateEvent1;

    private static final Logger logger = LoggerFactory.getLogger(AvroRideRequestProducer.class);

    public void sendRideRequest(PostRideRequest rideRequest) {
        AvroRideRequest ride = AvroRideRequest.newBuilder().setUserId(rideRequest.getUserid()).setRiders(rideRequest.getRiders()).build();
        logger.info("Send event 1 {}", ride);
        kafkaTemplateEvent1.send("ride-requests", ride);
    }

}
