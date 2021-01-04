package org.mddarr.socket.service;

import org.apache.kafka.streams.kstream.KStream;
import org.mddarr.rides.event.dto.AvroRide;
import org.mddarr.rides.event.dto.AvroRideRequest;
import org.mddarr.socket.service.model.CoordinatesResponse;
import org.mddarr.socket.service.model.Ride;
import org.mddarr.socket.service.model.requests.RideRequest;
import org.mddarr.socket.service.model.responses.RideRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;


@SpringBootApplication
public class SocketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocketServiceApplication.class, args);
	}

	@Autowired
	private SimpMessagingTemplate template;

//	@Bean
//	public Consumer<KStream<String, AvroRideRequest>> process_ride_requests() {
//		return (rideRequestStream) -> {
//			rideRequestStream.foreach((key, value) -> {
//				System.out.println("THE KEY IS AND THE VLAUE IS " + key + " " + value);
//				template.convertAndSend("/topic/rides/requests", value);
//			} );
//		};
//	}
//

	@Bean
	public Consumer<KStream<String, AvroRide>> process_rides() {
		return (rideRequestStream) -> {
			rideRequestStream.foreach((key, value) -> System.out.println("THE KEY IS AND THE VLAUE IS " + key + " " + value));
			Ride ride = new Ride("rideid1", "Charles Driver", "Eric User");
			template.convertAndSend("/topic/rides", ride);
		};
	}

	@Bean
	public Consumer<KStream<String, AvroRideRequest>> process_ride_requests() {
		return (rideRequestStream) -> {
			rideRequestStream.foreach((key, value) -> {
				System.out.println("THE KEY IS AND THE VLAUE IS " + key + " " + value);
				System.out.println("GETADF");
				RideRequestResponse rideRequestResponse = new RideRequestResponse();
				template.convertAndSend("/topic/rides/requests/alert", rideRequestResponse);
			});

		};
	}


}
