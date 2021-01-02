package org.mddarr.ride.coordinate.service.services;

import org.joda.time.DateTime;
import org.mddarr.ride.coordinate.service.models.CoordinatesMessage;
import org.mddarr.rides.event.dto.AvroRideCoordinate;
import org.mddarr.rides.event.dto.AvroRideRequest;
import org.mddarr.ride.coordinate.service.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AvroRideCoordinatesProducer implements AvroRideRequestInterface{

    @Autowired
    private KafkaTemplate<String, AvroRideCoordinate> kafkaTemplateEvent1;

    private static final Logger logger = LoggerFactory.getLogger(AvroRideCoordinatesProducer.class);

    public void sendRideRequest(CoordinatesMessage coordinatesMessage) {

        AvroRideCoordinate coordinate = AvroRideCoordinate.newBuilder().setRideid(coordinatesMessage.getRideid()).setLatitude(coordinatesMessage.getLatitude())
            .setLongitude(coordinatesMessage.getLongitude()).build();
        logger.info("Send Ride Coordinates 1 {}", coordinate);
        kafkaTemplateEvent1.send(Constants.COORDINATES_TOPIC, coordinate);
    }

}
