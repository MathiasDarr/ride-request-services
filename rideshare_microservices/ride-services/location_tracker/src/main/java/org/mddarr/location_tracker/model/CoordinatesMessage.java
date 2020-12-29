package org.mddarr.location_tracker.model;


import org.springframework.stereotype.Controller;

@Controller
public class CoordinatesMessage {

    public enum MessageType {
        CHAT, LEAVE, JOIN, START, END
    }

    private Double lat;
    private Double lng;
    private String sender;
    private MessageType type;

    public CoordinatesMessage() {
    }

    public CoordinatesMessage(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }


    public Double getLat() { return lat; }

    public void setLat(Double lat) { this.lat = lat; }

    public Double getLng() { return lng; }
    public void setLng(Double lng) { this.lng = lng; }

}
