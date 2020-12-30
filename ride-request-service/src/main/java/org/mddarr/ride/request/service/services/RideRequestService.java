package org.mddarr.ride.request.service.services;

import org.mddarr.ride.request.service.interfaces.RideRequestServiceInterface;
import org.mddarr.ride.request.service.models.requests.PostRideRequest;
import org.mddarr.ride.request.service.repository.RideRequestRepostiory;
import org.mddarr.ride.request.service.models.entities.RideRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class RideRequestService implements RideRequestServiceInterface {

//    @Autowired
//    RideRequestRepostiory rideRequestRepostiory;

    @Override
    public String postRideRequest(PostRideRequest postRideRequest) {
        String request_id = UUID.randomUUID().toString();

        RideRequest rideRequest = new RideRequest(request_id, new Date(), postRideRequest.getUserid(), postRideRequest.getRiders());
//        rideRequestRepostiory.save(rideRequest);

        return request_id;
    }


}
