package org.mddarr.ride.query.service.services;

import org.mddarr.ride.query.service.models.RideCoordinate;

import java.util.List;


public interface RideQueryServiceInterface {
    public List<RideCoordinate> getRideCoordinates(String rideID);

}
