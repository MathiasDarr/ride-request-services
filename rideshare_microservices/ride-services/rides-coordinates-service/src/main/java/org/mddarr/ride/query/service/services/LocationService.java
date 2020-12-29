package org.mddarr.ride.query.service.services;


import org.mddarr.ride.query.service.models.CoordinatesMessage;
import org.mddarr.ride.query.service.models.RideCoordinate;

import java.util.List;

public interface LocationService {
    public List<RideCoordinate> getRideCoordinates(String rideID);
    public void postCoordinatesData(CoordinatesMessage coordinatesMessage);
}
