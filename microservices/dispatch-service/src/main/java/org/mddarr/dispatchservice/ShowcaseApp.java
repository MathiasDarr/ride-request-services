package org.mddarr.dispatchservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.function.Function;

@SpringBootApplication
@ComponentScan(basePackages = "org.mddarr.dispatchservice")
public class ShowcaseApp {

    public static void main(String[] args) {
        SpringApplication.run(ShowcaseApp.class, args);
    }

    private static final Logger logger = LoggerFactory.getLogger(ShowcaseApp.class);

    @PostConstruct
    public void postInit() {
        logger.info("Application ShowcaseApp started!");
    }

    @Bean
    public Function<String, String> uppercase() {
        return v -> {
            v =  v.toUpperCase();
            System.out.println("Uppercase: " + v);
            return v;
        };
    }

    @Bean
    public Function<String, String> echo() {
        return v -> {
            System.out.println("Echo: " + v);
            return v;
        };
    }

}
