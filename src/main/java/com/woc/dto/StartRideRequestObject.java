package com.woc.dto;

public class StartRideRequestObject {

	String driverID;
	String rideId;
	String rideEndLocation;
	
	public String getDriverID() {
		return driverID;
	}
	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}
	public String getRideId() {
		return rideId;
	}
	public void setRideId(String rideId) {
		this.rideId = rideId;
	}
	public String getRideEndLocation() {
		return rideEndLocation;
	}
	public void setRideEndLocation(String rideEndLocation) {
		this.rideEndLocation = rideEndLocation;
	}

	
}
