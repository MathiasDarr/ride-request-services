package org.mddarr.ride.request.service.event;

import org.mddarr.ride.request.service.Constants;
import org.mddarr.ride.request.service.UatAbstractTest;



import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.mddarr.ride.request.service.models.requests.PostRideRequest;
import org.mddarr.rides.event.dto.AvroRideRequest;

import org.mddarr.ride.request.service.services.AvroRideRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageProducerTest extends UatAbstractTest {

    @Autowired
    private AvroRideRequestProducer avroRideRequestProducer;

    @Test
    public void should_send_ride_request() {
        AvroRideRequest avroRideRequest = new AvroRideRequest("Charles",2);
        avroRideRequestProducer.sendRideRequest(new PostRideRequest("Charles",2));
        ConsumerRecord<String, AvroRideRequest> singleRecord = KafkaTestUtils.getSingleRecord(rideRequestConsumer, Constants.ride_request_topic);
        assertThat(singleRecord).isNotNull();
    }


}