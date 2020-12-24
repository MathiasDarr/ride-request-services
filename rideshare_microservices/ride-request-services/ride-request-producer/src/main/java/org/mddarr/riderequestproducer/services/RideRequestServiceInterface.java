package org.mddarr.riderequestproducer.services;

import org.mddarr.riderequestproducer.models.RideRequest;
import org.mddarr.riderequests.AvroRideRequest;

public interface RideRequestServiceInterface {

    void sendRideRequest(RideRequest rideRequest);
}
