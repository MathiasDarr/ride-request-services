package org.mddarr.ride.coordinates.service.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@AllArgsConstructor
@Data
public class CoordinatesMessage {

    public enum MessageType {
        CHAT, LEAVE, JOIN, START, END
    }
    private String rideid;
    private Date time;
    private Double latitude;
    private Double longitude;

    private MessageType type;

    public CoordinatesMessage() {
    }

    public CoordinatesMessage(String trip_id, Date time, Double lat, Double lng ) {
        this.rideid = trip_id;
        this.time = time;
        this.latitude = lat;
        this.longitude = lng;

    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }


    public String getRideid() {
        return rideid;
    }

    public void setRideid(String rideid) {
        this.rideid = rideid;
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
