package org.mddarr.ride.query.service.models;

public class CoordinatesResponse {
    private Double latitude;
    private Double longitude;
    private String content;
    private MessageType type;


    public enum MessageType {
        CHAT, LEAVE, JOIN, START, END
    }

    public CoordinatesResponse(Double lat, Double lng) {
        this.latitude = lat;
        this.longitude = lng;
        this.content = "Lat: " + lat + " Lng: " + lng;
    }

    public CoordinatesResponse(String content){
        this.content = content;
    }

    public CoordinatesResponse() { }

    public Double getLat() {
        return latitude;
    }

    public void setLat(Double lat) {
        this.latitude = lat;
    }

    public Double getLng() {
        return longitude;
    }

    public void setLng(Double lng) {
        this.longitude = lng;
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
