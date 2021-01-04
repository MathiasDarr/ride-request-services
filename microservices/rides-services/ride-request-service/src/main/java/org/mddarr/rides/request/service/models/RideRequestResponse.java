package org.mddarr.rides.request.service.models;


public class RideRequestResponse {
    private String requestid;
    private String userid;
    private String destination;
    private int riders;

    public String getRideid() {
        return requestid;
    }

    public void setRideid(String rideid) {
        this.requestid = rideid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getRiders() {
        return riders;
    }

    public void setRiders(int riders) {
        this.riders = riders;
    }

}
