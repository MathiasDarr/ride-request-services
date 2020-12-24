package org.mddarr.ridereceiver;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.mddarr.ridereceiver.models.WordCount;
import org.mddarr.rides.event.dto.AvroRideRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
public class RideReceiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideReceiverApplication.class, args);
	}

	public static final String INPUT_TOPIC = "input";
	public static final String OUTPUT_TOPIC = "output";
	public static final int WINDOW_SIZE_MS = 30_000;


//	@Bean
//	public java.util.function.Consumer<KStream<String, String>> process() {
//
//		return input ->
//				input.foreach((key, value) -> {
//					System.out.println("Key: " + key + " Value: " + value);
//				});
//	}
	@Bean
	public Function<KStream<String, AvroRideRequest>, KStream<String, AvroRideRequest>>  process() {
		return (rideRequestStream) -> {
			rideRequestStream.foreach((key, value) -> System.out.println("THE KEY IS AND THE VLAUE IS " + key + " " + value));
			return rideRequestStream;

//			return 	userClicksStream.flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
//					.map((key, value) -> new KeyValue<>(value, value))
//					.groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
//					.windowedBy(TimeWindows.of(Duration.ofMillis(WINDOW_SIZE_MS)))
//					.count(Materialized.as("WordCounts-1"))
//					.toStream()
//					.map((key, value) -> new KeyValue<>(null, new AvroRideRequest("Charles", 3)));
		};
	}

//	@Bean
//	public Function<KStream<Bytes, String>, KStream<Bytes, WordCount>> process() {
//
//		return incoming_words -> incoming_words
//				.flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
//				.map((key, value) -> new KeyValue<>(value, value))
//				.groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
//				.windowedBy(TimeWindows.of(Duration.ofMillis(WINDOW_SIZE_MS)))
//				.count(Materialized.as("WordCounts-1"))
//				.toStream()
//				.map((key, value) -> new KeyValue<>(null, new WordCount(key.key(), value, new Date(key.window().start()), new Date(key.window().end()))));
//	}

}
