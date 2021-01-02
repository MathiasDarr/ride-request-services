package org.mddarr.producer;

import org.mddarr.producer.services.DataService;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;


import org.mddarr.rides.event.dto.AvroDriver;
import org.mddarr.rides.event.dto.AvroRide;
import org.mddarr.rides.event.dto.AvroRideCoordinate;
import org.mddarr.rides.event.dto.AvroRideRequest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.sql.*;
import java.util.*;

public class EventProducer {

    public static void main(String[] args) throws Exception {
        populate_drivers();
//        populateCoordinates();
//
        // populateDrivers();
        // populateRideRequests();
    }

    public static void populate_drivers() throws InterruptedException {
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

        List<AvroDriver> drivers = DataService.getDriversFromDB();
        System.out.println(drivers);

        Random rand = new Random();

        Set<AvroDriver> active_drivers = new HashSet<>();
        Set<AvroDriver> inactive_drivers = new HashSet<>();

        for(AvroDriver avroDriver: drivers){
            inactive_drivers.add(avroDriver);
        }

        while(true){

            List<AvroDriver> activating_drivers = new ArrayList<>();

            for(AvroDriver driver: inactive_drivers){
                double probablity = rand.nextDouble();
                if(probablity < .05){
                    activating_drivers.add(driver);
                }
            }
            System.out.println("THE NUMBER OF ACTIVE DRIVERS IS " + active_drivers.size());
//
//            for(AvroDriver driver: activating_drivers){
//
//                System.out.println("Activating driver " + driver);
//                inactive_drivers.remove(driver);
//
//            }
            List<AvroDriver> disactivating_drivers = new ArrayList<>();

            for(AvroDriver driver: active_drivers){
                double probablity = rand.nextDouble();
                if(probablity < .001){
                    disactivating_drivers.add(driver);
                }
            }
//
            for(AvroDriver driver: activating_drivers){

//                System.out.println("Activating driver " + driver);
                inactive_drivers.remove(driver);
                active_drivers.add(driver);
            }
            for(AvroDriver driver: disactivating_drivers){
//                System.out.println("Disactivating driver " + driver);
                inactive_drivers.add(driver);
                active_drivers.remove(driver);
            }

            Thread.sleep(3000);
        }
//        Set<AvroDriver> inactive_drivers =
//        drivers.forEach(driver -> {
//            System.out.println("Writing driver for '" + driver.getFirstname() + "' to input topic " +
//                    Constants.DRIVERS_TOPIC);
//            driverKafkaTemplate.sendDefault(driver);
//            Thread.sleep();
//        });
    }



    public static void populateRides() throws Exception{

        final Map<String, String> serdeConfig = Collections.singletonMap(
                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        // Set serializers and
        final SpecificAvroSerializer<AvroRide> purchaseEventSerializer = new SpecificAvroSerializer<>();
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

        DefaultKafkaProducerFactory<String, AvroRide> pf1 = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<String, AvroRide> rideRequestKafkaTemplate = new KafkaTemplate<>(pf1, true);
        rideRequestKafkaTemplate.setDefaultTopic(Constants.RIDE_REQUEST_TOPIC);

        AvroRide avroRide = new AvroRide("ride1","user1","driver1");
        rideRequestKafkaTemplate.sendDefault(avroRide);

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

        List<AvroDriver> drivers = DataService.getDriversFromDB();

        drivers.forEach(driver -> {
            System.out.println("Writing driver for '" + driver.getFirstname() + "' to input topic " +
                    Constants.DRIVERS_TOPIC);
            driverKafkaTemplate.sendDefault(driver);
        });
    }

    public static void populateCoordinates() throws Exception{
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


        DefaultKafkaProducerFactory<String, AvroRideCoordinate> pf1 = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<String, AvroRideCoordinate> driverKafkaTemplate = new KafkaTemplate<>(pf1, true);
        driverKafkaTemplate.setDefaultTopic(Constants.COORDINATES_TOPIC);

        while(true){
            AvroRideCoordinate avroRideCoordinate = new AvroRideCoordinate("ride1", 12.1, 12.0);
            driverKafkaTemplate.sendDefault(avroRideCoordinate);
            System.out.println("Writing ride coordinate for '" + avroRideCoordinate.getRideid() + "' to input topic " + Constants.COORDINATES_TOPIC);
            Thread.sleep(3000);

            avroRideCoordinate = new AvroRideCoordinate("ride2", 12.1, 12.0);
            driverKafkaTemplate.sendDefault(avroRideCoordinate);
            System.out.println("Writing ride coordinate for '" + avroRideCoordinate.getRideid() + "' to input topic " + Constants.COORDINATES_TOPIC);
            Thread.sleep(3000);
        }
    }
}
