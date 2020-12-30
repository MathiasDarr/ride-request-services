package org.mddarr.dispatchservice.producers;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.mddarr.dispatchservice.models.Ride;
import org.mddarr.rides.event.dto.AvroRide;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class RideProducer {

//    public static List

    public static List<AvroRide> getRidesFromDB(){
        List<Ride> rides = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgresdb",
                "postgres", "postgres");
             PreparedStatement pst = con.prepareStatement("SELECT rideid, userid, driverid FROM rides");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) { rides.add(new Ride(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }

        return rides.stream().map(RideProducer::map).collect(Collectors.toList());
    }

    private static AvroRide map(Ride ride){
        return new AvroRide(ride.getRideid(), ride.getUserid(),ride.getDriverid());
    }



    public static void main(String[] args) {
        populate_rides_topic();
    }


    public static void populate_rides_topic(){

        List<AvroRide> avroRides = getRidesFromDB();
        final Map<String, String> serdeConfig = Collections.singletonMap(
                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        // Set serializers and

        // Set serializers and

        final SpecificAvroSerializer<AvroRide> rideRequestSerializer = new SpecificAvroSerializer<>();
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

        DefaultKafkaProducerFactory<String, AvroRide> pf = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<String, AvroRide> template = new KafkaTemplate<>(pf, true);
        template.setDefaultTopic("rides");

        for(AvroRide ride: avroRides){
            template.sendDefault(ride);
        }

    }




}
