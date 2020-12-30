package org.mddarr.ride.coordinate.service.event;

import org.mddarr.ride.coordinate.service.UatAbstractTest;
import org.mddarr.ride.coordinate.service.services.AvroRideCoordinatesProducer;
import org.mddarr.ride.coordinate.service.Constants;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.mddarr.rides.event.dto.AvroRideRequest;

import org.mddarr.ride.coordinate.service.models.RideRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class RideRequestProducerTest extends UatAbstractTest {

    @Autowired
    private AvroRideCoordinatesProducer avroRideCoordinatesProducer;

    @Test
    public void should_send_ride_request() {
        avroRideCoordinatesProducer.sendRideRequest(new RideRequest("Charles",6));
        ConsumerRecord<String, AvroRideRequest> singleRecord = KafkaTestUtils.getSingleRecord(event1Consumer, Constants.Rides_TOPIC);
        assertThat(singleRecord).isNotNull();
    }


}