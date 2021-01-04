package org.mddarr.socket.service.model.requests;

public class RideRequest {
	private String userid;
	private String destination;
	private int riders;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getRiders() {
		return riders;
	}

	public void setRiders(int riders) {
		this.riders = riders;
	}
}
