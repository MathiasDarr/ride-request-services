package org.mddarr.ride.coordinate.service.services;

import org.mddarr.rides.event.dto.AvroRideRequest;
import org.mddarr.ride.coordinate.service.Constants;

import org.mddarr.ride.coordinate.service.models.RideRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AvroRideCoordinatesProducer implements AvroRideRequestInterface{

    @Autowired
    private KafkaTemplate<String, AvroRideRequest> kafkaTemplateEvent1;

    private static final Logger logger = LoggerFactory.getLogger(AvroRideCoordinatesProducer.class);

    public void sendRideRequest(RideRequest rideRequest) {
        AvroRideRequest ride = AvroRideRequest.newBuilder().setUserId(rideRequest.getUserid()).setRiders(rideRequest.getRiders()).build();
        logger.info("Send event 1 {}", ride);
        kafkaTemplateEvent1.send(Constants.Rides_TOPIC, ride);
    }

}
