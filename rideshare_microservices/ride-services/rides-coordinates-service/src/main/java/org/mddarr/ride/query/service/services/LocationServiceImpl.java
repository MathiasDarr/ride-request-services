package org.mddarr.ride.query.service.services;


import org.mddarr.ride.query.service.models.CoordinatesMessage;
import org.mddarr.ride.query.service.models.RideCoordinate;
import org.mddarr.ride.query.service.repository.RideCoordinatesRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LocationServiceImpl implements LocationService{

    private final RideCoordinatesRepository rideCoordinatesRepository;

    public LocationServiceImpl(RideCoordinatesRepository rideCoordinatesRepository){
        this.rideCoordinatesRepository = rideCoordinatesRepository;
    }

    @Override
    public List<RideCoordinate> getRideCoordinates(String rideID) {
        return null;
    }

    @Override
    public void postCoordinatesData(CoordinatesMessage coordinatesMessage){
        RideCoordinate cassandraTripDataPoint = new RideCoordinate(coordinatesMessage.getRideid(), coordinatesMessage.getTime(),coordinatesMessage.getLatitude(), coordinatesMessage.getLongitude());
        RideCoordinate inserted_data_item = rideCoordinatesRepository.insert(cassandraTripDataPoint);
    }
}
