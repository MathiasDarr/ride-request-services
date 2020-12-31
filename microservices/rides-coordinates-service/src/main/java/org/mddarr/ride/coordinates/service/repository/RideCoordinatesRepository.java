package org.mddarr.ride.coordinates.service.repository;


import org.mddarr.ride.coordinates.service.models.RideCoordinate;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideCoordinatesRepository extends CassandraRepository<RideCoordinate, String> {

}