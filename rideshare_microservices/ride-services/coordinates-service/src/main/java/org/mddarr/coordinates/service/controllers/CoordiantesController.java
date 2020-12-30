package org.mddarr.coordinates.service.controllers;



import org.mddarr.coordinates.service.models.CoordinatesMessage;
import org.mddarr.coordinates.service.models.requests.PostRideRequest;
import org.mddarr.coordinates.service.services.AvroCoordinatesProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoordiantesController {

    @Autowired
    AvroCoordinatesProducer avroCoordinatesProducer;

    @PutMapping(value = "/coordinates")
    public String putCoords(@RequestBody CoordinatesMessage coordinatesMessage){
        avroCoordinatesProducer.sendRideCoordinates(coordinatesMessage);
        return "dfd";
    }


}
