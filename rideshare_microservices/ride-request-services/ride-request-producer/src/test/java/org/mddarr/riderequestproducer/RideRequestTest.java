package org.mddarr.riderequestproducer;


import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.junit.jupiter.api.Test;
import org.mddarr.riderequestproducer.UatAbstractTest;
import org.mddarr.riderequestproducer.models.RideRequest;
import org.mddarr.riderequestproducer.services.RideRequestAvroProducer;
import org.mddarr.riderequests.AvroRideRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class RideRequestTest extends UatAbstractTest {

    @Autowired
    private RideRequestAvroProducer rideRequestAvroProducer;

    @Test
    void contextLoads(){

    }

    @Test
    public void should_send_ride_request() {
        rideRequestAvroProducer.sendRideRequest(new RideRequest("Pedro",2 ));
        ConsumerRecord<String, AvroRideRequest> singleRecord = KafkaTestUtils.getSingleRecord(avroRideRequestConsumer, "ride-requests");
        assertThat(singleRecord).isNotNull();
    }


}