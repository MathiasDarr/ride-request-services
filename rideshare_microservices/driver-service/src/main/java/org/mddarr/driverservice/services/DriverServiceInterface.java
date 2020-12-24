package org.mddarr.driverservice.services;

import org.mddarr.driverservice.models.Driver;
import org.mddarr.driverservice.models.requests.PostDriverRequest;


import java.util.List;
import java.util.Optional;

public interface DriverServiceInterface {

    List<Driver> getPatients();
    Optional<Driver> getPatientDetail(String id);
    String postPatient(PostDriverRequest postPatientRequest);
}