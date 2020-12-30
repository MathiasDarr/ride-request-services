package org.mddarr.ride.coordinate.service.event;

import org.mddarr.ride.coordinate.service.UatAbstractTest;
import org.mddarr.ride.coordinate.service.models.CoordinatesMessage;
import org.mddarr.ride.coordinate.service.services.AvroRideCoordinatesProducer;
import org.mddarr.ride.coordinate.service.Constants;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.mddarr.rides.event.dto.AvroRideRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class RideCoordinateProducerTest extends UatAbstractTest {

    @Autowired
    private AvroRideCoordinatesProducer avroRideCoordinatesProducer;

    @Test
    public void should_send_ride_coordinates() {
        CoordinatesMessage coordinatesMessage = new CoordinatesMessage("ride1",12.9,7.1);
        avroRideCoordinatesProducer.sendRideRequest(coordinatesMessage);
        ConsumerRecord<String, AvroRideRequest> singleRecord = KafkaTestUtils.getSingleRecord(avroRideRequestConsumer, Constants.COORDINATES_TOPIC);
        assertThat(singleRecord).isNotNull();
    }


}