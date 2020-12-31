package org.mddarr.rides.request.service.services;

import org.mddarr.rides.event.dto.AvroRideRequest;
import org.mddarr.rides.request.service.models.RideRequest;
import org.mddarr.rides.request.service.Constants;

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
        AvroRideRequest ride = AvroRideRequest.newBuilder()
                .setRequestId(request_id)
                .setUserId(rideRequest.getUserid())
                .setRiders(rideRequest.getRiders()).build();
        logger.info("Send event 1 {}", ride);
        kafkaTemplateEvent1.send(Constants.Rides_TOPIC, ride);
        return request_id;
    }

}
