package org.mddarr.coordinates.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class RidesCoordinatesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RidesCoordinatesApplication.class, args);
    }

    private static final Logger logger = LoggerFactory.getLogger(RidesCoordinatesApplication.class);

    @PostConstruct
    public void postInit() {
        logger.info("Application ShowcaseApp started!");
    }
}
