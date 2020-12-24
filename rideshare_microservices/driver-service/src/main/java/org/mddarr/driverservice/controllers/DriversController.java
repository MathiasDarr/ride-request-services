package org.mddarr.driverservice.controllers;


import org.mddarr.driverservice.models.Driver;
import org.mddarr.driverservice.models.requests.PostDriverRequest;
import org.mddarr.driverservice.services.DriverService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DriversController {

    private final DriverService driverService;

    public DriversController(DriverService driverService){
        this.driverService = driverService;
    }

    @GetMapping(value ="drivers")
    public List<Driver> getPatients(){
        return driverService.getPatients();
    }

    @GetMapping(value="drivers/{driver_id}")
    public Optional<Driver> getPatientDetail(@PathVariable String patient_id){
        return driverService.getPatientDetail(patient_id);
    }

    @PutMapping(value="drivers")
    public String createPatient(@RequestBody PostDriverRequest postDriverRequest){
        return driverService.postPatient(postDriverRequest);
    }
}
