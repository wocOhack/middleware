package com.woc.dto;

public class EndRideRequestObject {

	String driverID;
	String rideRequestId;
	String PIN;
	String rideStartLocation;
	
	
	public String getDriverID() {
		return driverID;
	}
	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}
	public String getRideRequestId() {
		return rideRequestId;
	}
	public void setRideRequestId(String rideRequestId) {
		this.rideRequestId = rideRequestId;
	}
	public String getPIN() {
		return PIN;
	}
	public void setPIN(String pIN) {
		PIN = pIN;
	}
	public String getRideStartLocation() {
		return rideStartLocation;
	}
	public void setRideStartLocation(String rideStartLocation) {
		this.rideStartLocation = rideStartLocation;
	}
	
}
