package org.mddarr.producer.models;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.Random;
import java.util.UUID;

public class DrivingSession {

    private String sessionid;


    private Driver driver;

    private String driverid;
    private Integer length_deviation = 150;
    private Integer session_length;
    private String state;
    private Random random_object;

    public DrivingSession(Driver driver, String session_id){
        this.sessionid = session_id;
        this.driver = driver;
        this.sessionid = UUID.randomUUID().toString();
        session_length = 0;
        random_object = new Random();
    }

    public String getDriverid() {
        return driverid;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }
    public void increment_session_length() {
         session_length += 1;
    }

    public void setSession_length(Integer session_length) {
        this.session_length = session_length;
    }

    private double probabilityThatSessionEnds(){
        /*
        This method retuns the CDF probability of a session ending at this time step
         */
        NormalDistribution normalDistribution = new NormalDistribution(driver.getAverage_shift_length(), length_deviation);
        return normalDistribution.cumulativeProbability(session_length);
    }

    public boolean verifySessionEnding(){
        /*
        This method returns true if the session is ending, false if not
         */
        return random_object.nextFloat() < probabilityThatSessionEnds();


    }



}
