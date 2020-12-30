package org.mddarr.ride.request.service.repository;

import org.mddarr.ride.request.service.models.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RideRequestRepostiory extends JpaRepository<RideRequest, String> {

}
