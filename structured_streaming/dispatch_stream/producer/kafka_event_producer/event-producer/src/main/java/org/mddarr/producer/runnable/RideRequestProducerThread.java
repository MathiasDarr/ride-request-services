/*
This file defines the TweetsAvroProducerThread class which implements the Runnable interface.  This class pushes tweets to a Kafka topic by reading data from a thread safe ArrayBlockingQueue


 */
package org.mddarr.producer.runnable;


import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.joda.time.DateTime;
import org.mddarr.rides.event.dto.AvroRideCoordinate;
import org.mddarr.rides.event.dto.AvroRideRequest;


import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;


public class RideRequestProducerThread implements Runnable {

    private final Log log = LogFactory.getLog(getClass());
    private final static String topic = "ride-requests";
    private final KafkaProducer<String, AvroRideRequest> avroRideRequestKafkaProducer;
    private final CountDownLatch latch;
    private int recordCount;


    public RideRequestProducerThread(CountDownLatch latch){
        this.latch = latch;
        this.avroRideRequestKafkaProducer = createKafkaProducer();
        this.recordCount +=1;
    }

    public KafkaProducer<String, AvroRideRequest> createKafkaProducer(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        properties.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        return new KafkaProducer<>(properties);
    }

    public void run() {
        while(latch.getCount() >0 ) {
            try {
                AvroRideRequest riderequest = new AvroRideRequest("riderequest1", "jesus@gmail.com", 3 );
                avroRideRequestKafkaProducer.send(new ProducerRecord<>(topic, riderequest));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        close();
    }

    public void close(){
            log.info("Closing Producer");
            avroRideRequestKafkaProducer.close();
            latch.countDown();
    }


}
