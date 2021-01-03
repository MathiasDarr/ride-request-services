package org.mddarr.producer.models;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.Random;
import java.util.UUID;

public class DrivingSession {

    private String sessionid;
    private Driver driver;
    private Integer length_deviation = 150;

    private Random random_object;
    private int preordained_session_length;
    private int session_length;
    private int session_start;
    private int session_end;

    public double getPreordained_session_length() {
        return preordained_session_length;
    }

    public DrivingSession(Driver driver, String session_id, int session_start){
        this.sessionid = session_id;
        this.driver = driver;
        this.sessionid = UUID.randomUUID().toString();

        random_object = new Random();

        Double determined_session_length = (random_object.nextGaussian() * length_deviation + driver.getAverage_shift_length());
        this.preordained_session_length = determined_session_length.intValue();

        if(preordained_session_length <= 15){
            preordained_session_length = 15;
        }

        this.session_start = session_start;
        this.session_end = preordained_session_length + session_start;

        System.out.println("INITIALIZING SESSION STARTING AT " + session_start + " which will end " + session_end + "with length  " + preordained_session_length);

        session_length += 1;

//        preordained_session_length = random_object.nextGaussian() * length_deviation + driver.getAverage_shift_length();
//        System.out.println("THE PREORDAINED LENGTH " + preordained_session_length);
    }

    public Driver getDriver() {
        return driver;
    }

    public void increment_session_length(){
        session_length += 1;
    }

    public int getSession_length() {
        return session_length;
    }

    public int getSession_start() {
        return session_start;
    }

    public int getSession_end() {
        return session_end;
    }

    public String getSessionid() {
        return sessionid;
    }


    //    private double probabilityThatSessionEnds(){
//        /*
//        This method retuns the CDF probability of a session ending at this time step
//         */
//        NormalDistribution normalDistribution = new NormalDistribution(driver.getAverage_shift_length(), length_deviation);
//        return normalDistribution.cumulativeProbability(session_length);
//    }
//
    public boolean verifySessionEnding(int iteration){
//        System.out.println("Iteration " +  iteration + " VERIFYING SESSION which has length " + session_length + " and will end at  " + session_end ) ;

//
        if(iteration >= session_end) {
//            System.out.println("THE SESSION IS ENDING ");
            return true;
        }
//        }else{
////            System.out.println("THE SESSION IS NOT ENDING ");
//        }

        return false;

    }



}
