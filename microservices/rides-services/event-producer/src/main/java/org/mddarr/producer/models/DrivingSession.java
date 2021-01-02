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
    private Double preordained_session_length;


    public DrivingSession(Driver driver, String session_id){
        this.sessionid = session_id;
        this.driver = driver;
        this.sessionid = UUID.randomUUID().toString();
        session_length = 0;
        random_object = new Random();
        preordained_session_length = random_object.nextGaussian() * length_deviation + driver.getAverage_shift_length();
//        System.out.println("THE PREORDAINED LENGTH " + preordained_session_length);
    }

    public String getDriverid() {
        return driverid;
    }

    public Driver getDriver() {
        return driver;
    }

    public void increment_session_length() {
         session_length += 1;
    }

    public Integer getSession_length() {
        return session_length;
    }

    private double probabilityThatSessionEnds(){
        /*
        This method retuns the CDF probability of a session ending at this time step
         */
        NormalDistribution normalDistribution = new NormalDistribution(driver.getAverage_shift_length(), length_deviation);
        return normalDistribution.cumulativeProbability(session_length);
    }

    public boolean verifySessionEnding(){
        System.out.println("VERIFYING SESSION with PREDORDAINED SESSION LENGTH " + preordained_session_length ) ;
        System.out.println("VERIFYING SESSION with session_length  " + session_length ) ;

        if(session_length >= preordained_session_length){
            System.out.println("THE SESSION IS ENDING ");
            return true;
        }else{
            System.out.println("THE SESSION IS NOT ENDING ");
        }

        return false;



        //        System.out.println("THE LENGTH OF THE SESSION IS " + session_length);
//        System.out.println(driver.getAverage_shift_length());
//        System.out.println("AVERAGE TRIP LENGTH " + driver.getAverage_shift_length());
//        System.out.println("THE probability that the session is ending is " + probabilityThatSessionEnds());
//
//
//        /*
//        This method returns true if the session is ending, false if not
//         */



    }



}
