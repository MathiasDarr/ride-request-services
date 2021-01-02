package org.mddarr.producer;

import org.mddarr.producer.models.Driver;
import org.mddarr.producer.models.DrivingSession;
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

import java.util.*;
import java.util.concurrent.Phaser;

public class EventProducer {

    public static void main(String[] args) throws Exception {
//        Driver driver = DataService.getDriver("1a49380a-b7a7-4ebd-9edd-9841312f6dd4");
//        System.out.println("THE DRIVER IS " + driver.getFirst_name() + " " + driver.getLast_name());
        populate_drivers();
//        populate_drivers();
//        DrivingSession drivingSession = new DrivingSession("driver1", 400);
////        drivingSession.probabilityThatSessionEnds(120);
////        System.out.println("The probability that the session will end at time 500 is " + drivingSession.probabilityThatSessionEnds(400));
//        DataService.insertDrivingSession("1a49380a-b7a7-4ebd-9edd-9841312f6dd4", 300);
//        populate_drivers();
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

        List<Driver> drivers = DataService.getDriversFromDB();
        System.out.println(drivers);

        Random rand = new Random();

        Set<Driver> active_drivers = new HashSet<>();
        Set<Driver> inactive_drivers = new HashSet<>();
        Set<DrivingSession> active_sessions = new HashSet<>();

        inactive_drivers.addAll(drivers);

        while(true){

            List<Driver> activating_drivers = new ArrayList<>();
            // Select  the drivers from the inactive pool to activate sessions
            for(Driver driver: inactive_drivers){
                double probablity = rand.nextDouble();
                if(probablity < .02){
                    activating_drivers.add(driver);
                }
            }

//
            Iterator<DrivingSession> drivingSessionIterator = active_sessions.iterator();

            while(drivingSessionIterator.hasNext()){
                DrivingSession session = drivingSessionIterator.next();
                if(session.verifySessionEnding()){
                    Driver driver = session.getDriver();
                    inactive_drivers.add(driver);
                    System.out.println("SESSION WITH DRIVER " + session.getDriver().getFirst_name() + " " + session.getDriver().getLast_name() + " HAS ENDED AT LENGTH " + session.getSession_length());
                    drivingSessionIterator.remove();
                    active_drivers.remove(driver);
                }else{
                    session.increment_session_length();
                }
            }

            for(Driver driver: activating_drivers){
                inactive_drivers.remove(driver);
                active_drivers.add(driver);
//                String session_id = DataService.insertDrivingSession(driver.getDriverid(), driver.getAverage_shift_length());
                DrivingSession drivingSession = new DrivingSession(driver, UUID.randomUUID().toString());
                active_sessions.add(drivingSession);
            }

            Thread.sleep(300);
        }
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


//    public static void populateDrivers() throws Exception{
//        final Map<String, String> serdeConfig = Collections.singletonMap(
//                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
//        // Set serializers and
//        final SpecificAvroSerializer<AvroDriver> purchaseEventSerializer = new SpecificAvroSerializer<>();
//        purchaseEventSerializer.configure(serdeConfig, false);
//
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
//        DefaultKafkaProducerFactory<String, AvroDriver> pf1 = new DefaultKafkaProducerFactory<>(props);
//        KafkaTemplate<String, AvroDriver> driverKafkaTemplate = new KafkaTemplate<>(pf1, true);
//        driverKafkaTemplate.setDefaultTopic(Constants.DRIVERS_TOPIC);
//
//        List<AvroDriver> drivers = DataService.getDriversFromDB();
//
//        drivers.forEach(driver -> {
//            System.out.println("Writing driver for '" + driver.getFirstname() + "' to input topic " +
//                    Constants.DRIVERS_TOPIC);
//            driverKafkaTemplate.sendDefault(driver);
//        });
//    }

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
