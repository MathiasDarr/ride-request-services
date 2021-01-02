package org.mddarr.producer.models;

public class Driver {
    String driverid;
    String first_name;
    String last_name;
    Integer average_shift_length;

    public Driver(){}

    public Driver(String driverid, String first_name, String last_name, Integer average_shift_length) {
        this.driverid = driverid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.average_shift_length = average_shift_length;
    }

    public String getDriverid() {
        return driverid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Integer getAverage_shift_length() {
        return average_shift_length;
    }

    public void setAverage_shift_length(Integer average_shift_length) {
        this.average_shift_length = average_shift_length;
    }
}
