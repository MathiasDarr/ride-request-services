package org.mddarr.ride.coordinate.service;

import org.apache.kafka.streams.kstream.KStream;
import org.mddarr.rides.event.dto.AvroDriver;
import org.mddarr.rides.event.dto.AvroRide;
import org.mddarr.rides.event.dto.AvroRideRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
public class RideCoordinateApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideCoordinateApplication.class, args);
    }

    private static final Logger logger = LoggerFactory.getLogger(RideCoordinateApplication.class);

    @PostConstruct
    public void postInit() {
        logger.info("Application ShowcaseApp started!");
    }

    @Bean
    public Consumer<KStream<String, AvroRide>> processed_rides() {
        return (rideStream) -> {
            rideStream.foreach((key, value) -> System.out.println("THE KEY IS AND THE VLAUE IS " + key + " " + value));
        };
    }

}
