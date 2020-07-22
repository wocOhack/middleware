package com.woc.dto;

public class RideRequestUpdateObject {

	private Long rideRequestID;
	private Long driverID;
	private String reponse;
	
	
	public Long getRideRequestID() {
		return rideRequestID;
	}
	public void setRideRequestID(Long rideRequestID) {
		this.rideRequestID = rideRequestID;
	}
	public Long getDriverID() {
		return driverID;
	}
	public void setDriverID(Long driverID) {
		this.driverID = driverID;
	}
	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	
	
}
