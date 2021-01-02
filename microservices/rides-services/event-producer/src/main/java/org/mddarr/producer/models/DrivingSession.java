package org.mddarr.producer.models;

import org.apache.commons.math3.distribution.NormalDistribution;
public class DrivingSession {

    private String driverid;
    private Integer drivers_average_trip_length;
    private Integer length_deviation = 150;
    private Integer session_length;
    private String completed_trips;
    private String state;

    public DrivingSession(String driverid, Integer drivers_average_trip_length){
        this.driverid = driverid;
        this.drivers_average_trip_length = drivers_average_trip_length;

    }


    public double probabilityThatSessionEnds(Integer length){
        /*
        This
         */
        System.out.println("DFDF");
        NormalDistribution normalDistribution = new NormalDistribution(drivers_average_trip_length, length_deviation);
        return normalDistribution.cumulativeProbability(length);
//        return normalDistribution.cumulativeProbability(session_length);
    }


}
