package org.mddarr.dispatchservice.models;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name ="rides")
@AllArgsConstructor
@NoArgsConstructor
public class Ride {

    @Id
    private String rideid;

    @Column
    private String userid;

    @Column String driverid;


    public String getRideid() {
        return rideid;
    }

    public void setRideid(String rideid) {
        this.rideid = rideid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }


}
