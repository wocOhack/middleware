package com.woc.dto;

import java.util.Date;

public class EndRideRequestDto {

	private Long tripId;
	private String city;
	private String pickup;
	private String destination;
	private Double distanceInMeters;
	private String distanceStr;
	private Long durationInSecs;
	private String durationStr;

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPickup() {
		return pickup;
	}

	public void setPickup(String pickup) {
		this.pickup = pickup;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Double getDistanceInMeters() {
		return distanceInMeters;
	}

	public void setDistanceInMeters(Double distanceInMeters) {
		this.distanceInMeters = distanceInMeters;
	}

	public String getDistanceStr() {
		return distanceStr;
	}

	public void setDistanceStr(String distanceStr) {
		this.distanceStr = distanceStr;
	}

	public Long getDurationInSecs() {
		return durationInSecs;
	}

	public void setDurationInSecs(Long durationInSecs) {
		this.durationInSecs = durationInSecs;
	}

	public String getDurationStr() {
		return durationStr;
	}

	public void setDurationStr(String durationStr) {
		this.durationStr = durationStr;
	}
}
