package org.mddarr.producer;

import org.mddarr.producer.runnable.RideRequestProducerThread;
import org.mddarr.producer.services.DataService;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;


import org.mddarr.rides.event.dto.AvroDriver;
import org.mddarr.rides.event.dto.AvroRideCoordinate;
import org.mddarr.rides.event.dto.AvroRideRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

public class EventProducer {

    private Logger log = LoggerFactory.getLogger(EventProducer.class.getSimpleName());
    private ExecutorService executor;
    private CountDownLatch latch;
    private final RideRequestProducerThread rideRequestProducerThread;


    public static void main(String[] args) throws Exception {

        System.out.println("HDFDFA");
        EventProducer app = new EventProducer(args);
        app.start();
//        populateCoordinates();
        // populateDrivers();
        // populateRideRequests();
    }


    private EventProducer(String[] arguments){
        int nrides = 0;
        latch = new CountDownLatch(nrides+1);
        executor = Executors.newFixedThreadPool(nrides+1);
        rideRequestProducerThread = new RideRequestProducerThread(latch);
    }


    public void start() {
        System.out.println("WDDFDF");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!executor.isShutdown()) {
                log.info("Shutdown requested");
                shutdown();
            }
        }));
//
        System.out.println("Application started!");
//        executor.submit(rideRequestProducerThread);
////        for(TweetsAvroProducerThread thread: avroProducerThreads){
////            executor.submit(thread);
////        }
//        log.info("Stuff submit");
//        try {
//            log.info("Latch await");
//            latch.await();
//            log.info("Threads completed");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            shutdown();
//            log.info("Application closed succesfully");
//        }

    }

    private void shutdown() {
        if (!executor.isShutdown()) {
            log.info("Shutting down");
            executor.shutdownNow();
            try {
                if (!executor.awaitTermination(2000, TimeUnit.MILLISECONDS)) { //optional *
                    log.warn("Executor did not terminate in the specified time."); //optional *
                    List<Runnable> droppedTasks = executor.shutdownNow(); //optional **
                    log.warn("Executor was abruptly shut down. " + droppedTasks.size() + " tasks will not be executed."); //optional **
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }









    public static void populateRideRequests() throws Exception{
        final Map<String, String> serdeConfig = Collections.singletonMap(
                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        // Set serializers and
        final SpecificAvroSerializer<AvroRideRequest> purchaseEventSerializer = new SpecificAvroSerializer<>();
        purchaseEventSerializer.configure(serdeConfig, false);

        Map<String, Object> props = new HashMap<>();
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, purchaseEventSerializer.getClass());


        DefaultKafkaProducerFactory<String, AvroRideRequest> pf1 = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<String, AvroRideRequest> rideRequestKafkaTemplate = new KafkaTemplate<>(pf1, true);
        rideRequestKafkaTemplate.setDefaultTopic(Constants.RIDE_REQUEST_TOPIC);

        List<AvroRideRequest> rideRequests = DataService.getRideRequestsFromDB();

        rideRequests.forEach(rideRequest -> {
            System.out.println("Writing ride request for '" + rideRequest.getRequestId() + "' to input topic " +
                    Constants.RIDE_REQUEST_TOPIC);
            rideRequestKafkaTemplate.sendDefault(rideRequest);
        });
    }


    public static void populateDrivers() throws Exception{
        final Map<String, String> serdeConfig = Collections.singletonMap(
                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        // Set serializers and
        final SpecificAvroSerializer<AvroDriver> purchaseEventSerializer = new SpecificAvroSerializer<>();
        purchaseEventSerializer.configure(serdeConfig, false);


        Map<String, Object> props = new HashMap<>();
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, purchaseEventSerializer.getClass());


        DefaultKafkaProducerFactory<String, AvroDriver> pf1 = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<String, AvroDriver> driverKafkaTemplate = new KafkaTemplate<>(pf1, true);
        driverKafkaTemplate.setDefaultTopic(Constants.DRIVERS_TOPIC);

        List<AvroDriver> drivers = DataService.getProductsFromDB();

        drivers.forEach(driver -> {
            System.out.println("Writing driver for '" + driver.getFirstname() + "' to input topic " +
                    Constants.DRIVERS_TOPIC);
            driverKafkaTemplate.sendDefault(driver);
        });
    }

//    public static void populateCoordinates() throws Exception{
//        final Map<String, String> serdeConfig = Collections.singletonMap(
//                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
//
//        final SpecificAvroSerializer<AvroDriver> purchaseEventSerializer = new SpecificAvroSerializer<>();
//        purchaseEventSerializer.configure(serdeConfig, false);
//
//        Map<String, Object> props = new HashMap<>();
//        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ProducerConfig.RETRIES_CONFIG, 0);
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
//        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
//        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, purchaseEventSerializer.getClass());
//
//
//        DefaultKafkaProducerFactory<String, AvroRideCoordinate> pf1 = new DefaultKafkaProducerFactory<>(props);
//        KafkaTemplate<String, AvroRideCoordinate> driverKafkaTemplate = new KafkaTemplate<>(pf1, true);
//        driverKafkaTemplate.setDefaultTopic(Constants.COORDINATES_TOPIC);
//        int i = 0;
//        while(true){
//            AvroRideCoordinate avroRideCoordinate = new AvroRideCoordinate("ride1", 12.1+i, 12.0-i);
//            driverKafkaTemplate.sendDefault(avroRideCoordinate);
//            System.out.println("Writing ride coordinate for '" + avroRideCoordinate.getRideid() + "' to input topic " + Constants.COORDINATES_TOPIC);
//            Thread.sleep(3000);
//
//            avroRideCoordinate = new AvroRideCoordinate("ride2", 12.1, 12.+2*i);
//            driverKafkaTemplate.sendDefault(avroRideCoordinate);
//            System.out.println("Writing ride coordinate for '" + avroRideCoordinate.getRideid() + "' to input topic " + Constants.COORDINATES_TOPIC);
//            Thread.sleep(3000);
//            i +=1;
//        }
//    }
}
