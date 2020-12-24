package org.mddarr.requestprocessor.services;

import org.mddarr.riderequests.AvroRideRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class RideRequestAvroProducer implements RideRequestServiceInterface{

    @Autowired
    private KafkaTemplate<String, AvroRideRequest> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RideRequestAvroProducer.class);

    @Override
    public void sendRideRequest(AvroRideRequest avroRideRequest) {
        logger.info("Send ride request {}",avroRideRequest );
        kafkaTemplate.send("ride-requests","key1", avroRideRequest);
    }
}
