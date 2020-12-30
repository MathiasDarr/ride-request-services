package org.mddarr.ride.coordinates.service.services;


import org.mddarr.ride.coordinates.service.repository.RideCoordinatesRepository;
import org.mddarr.ride.coordinates.service.models.CoordinatesMessage;
import org.mddarr.ride.coordinates.service.models.RideCoordinate;
import org.springframework.stereotype.Service;


@Service
public class CoordinatesService implements LocationServiceInterface {

    private final RideCoordinatesRepository rideCoordinatesRepository;

    public CoordinatesService(RideCoordinatesRepository rideCoordinatesRepository){
        this.rideCoordinatesRepository = rideCoordinatesRepository;
    }

    @Override
    public void postCoordinatesData(CoordinatesMessage coordinatesMessage){
        RideCoordinate cassandraTripDataPoint = new RideCoordinate(coordinatesMessage.getRideid(), coordinatesMessage.getTime(),coordinatesMessage.getLatitude(), coordinatesMessage.getLongitude());
        RideCoordinate inserted_data_item = rideCoordinatesRepository.insert(cassandraTripDataPoint);
    }
}
