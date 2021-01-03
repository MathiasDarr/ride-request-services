package org.mddarr.producer.models;

public class User {
    String userid;
    String first_name;
    String last_name;


    public User(){}

    public User(String userid, String first_name, String last_name) {
        this.userid = userid;
        this.first_name = first_name;
        this.last_name = last_name;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
