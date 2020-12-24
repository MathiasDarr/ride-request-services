package org.mddarr.rideservice.event;

import org.mddarr.rideservice.Constants;
import org.mddarr.rideservice.UatAbstractTest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.mddarr.payments.event.dto.Event1;
import org.mddarr.payments.event.dto.Event2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageProducerTest extends UatAbstractTest {

    @Autowired
    private MessageProducer messageProducer;

    @Test
    public void should_send_event1() {
        messageProducer.sendEvent1();

        ConsumerRecord<String, Event1> singleRecord = KafkaTestUtils.getSingleRecord(event1Consumer, Constants.EVENT_1_TOPIC);
        assertThat(singleRecord).isNotNull();
    }

    @Test
    public void should_send_event2() {
        messageProducer.sendEvent2();

        ConsumerRecord<String, Event2> singleRecord = KafkaTestUtils.getSingleRecord(event2Consumer, Constants.EVENT_2_TOPIC);
        assertThat(singleRecord).isNotNull();
    }
}