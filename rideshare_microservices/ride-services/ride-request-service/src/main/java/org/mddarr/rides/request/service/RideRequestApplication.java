package org.mddarr.rides.request.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class RideRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideRequestApplication.class, args);
    }

    private static final Logger logger = LoggerFactory.getLogger(RideRequestApplication.class);

    @PostConstruct
    public void postInit() {
        logger.info("Application ShowcaseApp started!");
    }
}
