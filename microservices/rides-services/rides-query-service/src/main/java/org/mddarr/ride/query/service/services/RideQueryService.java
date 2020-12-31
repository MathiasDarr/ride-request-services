package org.mddarr.ride.query.service.services;


import org.mddarr.ride.query.service.models.RideCoordinate;
import org.mddarr.ride.query.service.repository.RideCoordinatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RideQueryService implements RideQueryServiceInterface {

    @Autowired
    RideCoordinatesRepository rideCoordinatesRepository;

//    public RideQueryService(RideCoordinatesRepository rideCoordinatesRepository){
//        this.rideCoordinatesRepository = rideCoordinatesRepository;
//    }

    @Override
    public List<RideCoordinate> getRideCoordinates(String rideID) {
        return rideCoordinatesRepository.getRideCoordinates(rideID);
    }


}
