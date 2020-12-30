package org.mddarr.producer;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.mddarr.rides.event.dto.AvroDriver;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.sql.*;
import java.util.*;

public class EventProducer {

    public static void main(String[] args) throws Exception {
        EventProducer.populateDrivers();
    }

    public static List<AvroDriver> getProductsFromDB(){
        List<AvroDriver> avroDrivers = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgresdb",
                "postgres", "postgres");
             PreparedStatement pst = con.prepareStatement("SELECT driver_id, first_name, last_name FROM drivers");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                avroDrivers.add(new AvroDriver (rs.getString(1),rs.getString(2),rs.getString(3) ));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        return avroDrivers;
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

        List<AvroDriver> drivers = getProductsFromDB();

        drivers.forEach(driver -> {
            System.out.println("Writing driver for '" + driver.getFirstname() + "' to input topic " +
                    Constants.DRIVERS_TOPIC);
            driverKafkaTemplate.sendDefault(driver);
        });


    }

}
