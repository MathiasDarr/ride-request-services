package org.mddarr.coordinates.service.services;
import org.joda.time.DateTime;
import org.mddarr.coordinates.service.Constants;
import org.mddarr.coordinates.service.interfaces.AvroCoordinatesInterface;
import org.mddarr.coordinates.service.models.CoordinatesMessage;
import org.mddarr.rides.event.dto.AvroRideCoordinate;
import org.mddarr.rides.event.dto.AvroRideRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;



@Service
public class AvroCoordinatesProducer implements AvroCoordinatesInterface {

    @Autowired
    private KafkaTemplate<String, AvroRideCoordinate> coordinateKafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(AvroCoordinatesProducer.class);

    public void sendRideCoordinates(CoordinatesMessage coordinatesMessage) {
        AvroRideCoordinate avroRideCoordinate = new AvroRideCoordinate("ride1", 12.1, 21.3);
        AvroRideCoordinate coordinate = AvroRideCoordinate.newBuilder().setRideid(coordinatesMessage.getRideid()).setLatitude(coordinatesMessage.getLatitude())
                .setLongitude(coordinatesMessage.getLongitude()).build();
        logger.info("Send ride coordinate message {}", coordinate);
        coordinateKafkaTemplate.send(Constants.COORDINATES_TOPIC, coordinate);
    }

}
