package org.mddarr.ride.coordinates.service.services;


import org.mddarr.ride.coordinates.service.models.CoordinatesMessage;
import org.mddarr.ride.coordinates.service.models.RideCoordinate;

import java.util.List;

public interface LocationServiceInterface {
    public void postCoordinatesData(CoordinatesMessage coordinatesMessage);
}
