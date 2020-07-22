package com.woc.dto;

import java.io.Serializable;

public class Ride implements Serializable{

	private static final long serialVersionUID = 251350002009366387L;
	private Long rideID;
	private String fromLocation;
	private String toLocation;
	private Rider rider;
	private Driver driver;
	
	
	public Long getRideID() {
		return rideID;
	}
	public void setRideID(Long rideID) {
		this.rideID = rideID;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public Rider getRider() {
		return rider;
	}
	public void setRider(Rider rider) {
		this.rider = rider;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

}
