package org.mddarr.socket.service.services;


import org.mddarr.socket.service.model.requests.RideRequest;

public interface AvroRideRequestInterface {
    public String sendRideRequest(RideRequest rideRequest);
}
