package org.mddarr.requestprocessor.services;

import org.mddarr.riderequests.AvroRideRequest;

public interface RideRequestServiceInterface {

    void sendRideRequest(AvroRideRequest avroRideRequest);
}
