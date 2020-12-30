package org.mddarr.driversservice.services;


import org.mddarr.driversservice.Constants;
import org.mddarr.rides.event.dto.AvroDriver;
import org.mddarr.rides.event.dto.AvroRideRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AvroDriverProducer implements AvroDriverInterface {
////
    @Autowired
    private KafkaTemplate<String, AvroDriver> kafkaTemplateEvent1;

    private static final Logger logger = LoggerFactory.getLogger(AvroDriverProducer.class);

    public void postDriver() {

        AvroDriver avroDriver = new AvroDriver("c7c6ad96-5a11-46a9-b50d-3a842348d157","John","Cash");
        logger.info("Send event 1 {}", avroDriver);
        kafkaTemplateEvent1.send(Constants.DRIVERS_TOPIC, avroDriver);
    }

}
