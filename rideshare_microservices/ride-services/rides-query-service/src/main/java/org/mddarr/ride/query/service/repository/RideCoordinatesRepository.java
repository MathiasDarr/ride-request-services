package org.mddarr.ride.query.service.repository;

import org.mddarr.ride.query.service.models.RideCoordinate;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideCoordinatesRepository extends CassandraRepository<RideCoordinate, Long> {

}