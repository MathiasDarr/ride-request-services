package org.mddarr.socket.service.model;


import org.springframework.stereotype.Controller;

@Controller
public class CoordinatesMessage {

    private String rideID;
    private double lat;
    private double lng;


    public CoordinatesMessage() {
    }

    public String getRideID() {
        return rideID;
    }

    public void setRideID(String rideID) {
        this.rideID = rideID;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
