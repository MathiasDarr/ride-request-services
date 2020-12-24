package org.mddarr.request_receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class RideRequestProcessorMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideRequestProcessorMainApplication.class, args);
	}

	@Bean
	public Function<String, String> uppercaseFunction() {
		return String::toUpperCase;
	}

}
