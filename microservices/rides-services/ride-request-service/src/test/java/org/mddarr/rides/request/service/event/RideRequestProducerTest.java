package org.mddarr.rides.request.service.event;

import org.mddarr.rides.event.dto.AvroRideRequest;
import org.mddarr.rides.request.service.models.RideRequest;
import org.mddarr.rides.request.service.Constants;
import org.mddarr.rides.request.service.UatAbstractTest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;


import org.mddarr.rides.request.service.services.AvroRideRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class RideRequestProducerTest extends UatAbstractTest {

    @Autowired
    private AvroRideRequestProducer avroRideRequestProducer;

    @Test
    public void should_send_ride_request() {
        avroRideRequestProducer.sendRideRequest(new RideRequest("Charles",6));
        ConsumerRecord<String, AvroRideRequest> singleRecord = KafkaTestUtils.getSingleRecord(event1Consumer, Constants.Rides_TOPIC);
        assertThat(singleRecord).isNotNull();
    }

    @Test
    public void contextLoads(){

    }
}