package org.mddarr.ride.coordinates.service.services;


import org.joda.time.DateTime;
import org.mddarr.ride.coordinates.service.repository.RideCoordinatesRepository;
import org.mddarr.ride.coordinates.service.models.CoordinatesMessage;
import org.mddarr.ride.coordinates.service.models.RideCoordinate;
import org.mddarr.rides.event.dto.AvroRideCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class CoordinatesService implements LocationServiceInterface {

//    private final RideCoordinatesRepository rideCoordinatesRepository;


    @Autowired
    private KafkaTemplate<String, AvroRideCoordinate> kafkaCoordinatesTemplate;

//    public CoordinatesService(RideCoordinatesRepository rideCoordinatesRepository){
//        this.rideCoordinatesRepository = rideCoordinatesRepository;
//    }

    @Override
    public void postCoordinatesData(CoordinatesMessage coordinatesMessage){
//        RideCoordinate cassandraTripDataPoint = new RideCoordinate(coordinatesMessage.getRideid(), coordinatesMessage.getTime(),coordinatesMessage.getLatitude(), coordinatesMessage.getLongitude());
        kafkaCoordinatesTemplate.setDefaultTopic("ride-requests");
        kafkaCoordinatesTemplate.sendDefault(new AvroRideCoordinate(coordinatesMessage.getRideid(), DateTime.now(),coordinatesMessage.getLatitude(), coordinatesMessage.getLongitude()));
//        return inserted_data_item;
    }
}
