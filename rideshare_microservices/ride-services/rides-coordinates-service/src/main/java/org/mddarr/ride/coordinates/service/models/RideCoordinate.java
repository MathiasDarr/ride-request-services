package org.mddarr.ride.query.service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;


@Table("trip_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideCoordinate {

    @PrimaryKeyColumn(name="rideid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String rideid;

    @PrimaryKeyColumn(name="time", ordinal = 1, type = PrimaryKeyType.CLUSTERED )
    private  Date time;

    private Double latitude;
    private Double longitude;


    public String getRideid() {
        return rideid;
    }

    public void setRideid(String rideid) {
        this.rideid = rideid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}


