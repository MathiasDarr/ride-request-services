package org.mddarr.producer.models;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.Random;
import java.util.UUID;

public class RideRequestSession {

    private String requestid;
    private User user;
    private int request_time;
    Random random_object;

    public RideRequestSession(User user, int request_time){
        this.request_time = request_time;
        this.user = user;
        this.requestid = UUID.randomUUID().toString();
        random_object = new Random();

        System.out.println("CREATING RIDE REQUEST STARTING AT " + request_time + " from user " + user.getFirst_name() + " " + user.getLast_name());

//        preordained_session_length = random_object.nextGaussian() * length_deviation + driver.getAverage_shift_length();
//        System.out.println("THE PREORDAINED LENGTH " + preordained_session_length);
    }

    public User getUser() {
        return user;
    }

    public String getRequestid() {
        return requestid;
    }

    public int getRequest_time() {
        return request_time;
    }

}
