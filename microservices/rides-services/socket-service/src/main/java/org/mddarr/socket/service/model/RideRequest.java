package org.mddarr.socket.service.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {
    private String userid;
    private int riders;
    private String destination;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getRiders() {
        return riders;
    }

    public void setRiders(int riders) {
        this.riders = riders;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
