package org.mddarr.location_tracker.model;

public class CoordinatesResponse {
    private Double lat;
    private Double lng;
    private String content;
    private MessageType type;


    public enum MessageType {
        CHAT, LEAVE, JOIN, START, END
    }

    public CoordinatesResponse(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
        this.content = "Lat: " + lat + " Lng: " + lng;
    }

    public CoordinatesResponse(String content){
        this.content = content;
    }

    public CoordinatesResponse() { }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
