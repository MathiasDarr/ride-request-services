package org.mddarr.riderequestproducer.services;

import org.mddarr.riderequestproducer.models.RideRequest;
import org.mddarr.riderequests.AvroRideRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RideRequestAvroProducer implements RideRequestServiceInterface{

    public final String TOPIC = "ride-requests";

    @Autowired
    private KafkaTemplate<String, AvroRideRequest> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RideRequestAvroProducer.class);

    @Override
    public void sendRideRequest(RideRequest rideRequest) {
        logger.info("Send ride request {}", rideRequest );
        kafkaTemplate.send(TOPIC,"key1", new AvroRideRequest(rideRequest.getUser(), rideRequest.getRiders()));
    }
}
