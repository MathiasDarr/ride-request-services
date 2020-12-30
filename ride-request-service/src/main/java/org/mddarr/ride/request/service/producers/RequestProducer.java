package org.mddarr.ride.request.service.producers;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.mddarr.rides.event.dto.AvroRideRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestProducer {

    public static void main(String[] args) throws Exception {
        populateRideRequestTopics();
    }


    public static void populateRideRequestTopics() throws Exception{

        final Map<String, String> serdeConfig = Collections.singletonMap(
                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        // Set serializers and

        final SpecificAvroSerializer<AvroRideRequest> rideRequestSerializer = new SpecificAvroSerializer<>();
        rideRequestSerializer.configure(serdeConfig, false);

        Map<String, Object> props = new HashMap<>();
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, rideRequestSerializer.getClass());

//        Map<String, Object> props1 = new HashMap<>(props);
//        props1.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props1.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, rideRequestSerializer.getClass());

        DefaultKafkaProducerFactory<String, AvroRideRequest> pf = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<String, AvroRideRequest> template = new KafkaTemplate<>(pf, true);
        template.setDefaultTopic("ride-requests");

        while (true) {
            System.out.println("Writing ride request event for user" );
            template.sendDefault("uk", new AvroRideRequest("user1", 2));
            Thread.sleep(100L);
        }
    }
}
