package org.mddarr.ride.coordinate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

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
}
