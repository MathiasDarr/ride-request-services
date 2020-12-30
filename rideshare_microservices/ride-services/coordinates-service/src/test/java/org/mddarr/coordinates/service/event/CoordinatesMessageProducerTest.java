package org.mddarr.coordinates.service.event;

import org.joda.time.DateTime;
import org.mddarr.coordinates.service.Constants;
import org.mddarr.coordinates.service.UatAbstractTest;
import org.mddarr.coordinates.service.models.CoordinatesMessage;
import org.mddarr.coordinates.service.services.AvroCoordinatesProducer;



import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.mddarr.rides.event.dto.AvroRideCoordinate;
import org.mddarr.rides.event.dto.AvroRideRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordinatesMessageProducerTest extends UatAbstractTest {

    @Autowired
    private AvroCoordinatesProducer avroCoordinatesProducer;

    @Test
    public void should_send_ride_request() {
        CoordinatesMessage coordinatesMessage = new CoordinatesMessage("ride1", 12.1, 12.0);
        avroCoordinatesProducer.sendRideCoordinates(coordinatesMessage);
        ConsumerRecord<String, AvroRideRequest> singleRecord = KafkaTestUtils.getSingleRecord(event1Consumer, Constants.topic);
        assertThat(singleRecord).isNotNull();
    }


}