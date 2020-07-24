package com.woc.dto;

import java.io.Serializable;

public class RideRequestObject implements Serializable{

	private static final long serialVersionUID = -4622010308207657761L;

	Long riderID;
	String sourceLocation;
	String destinationLocation;
	Double sourceLattitude;
	Double destinationLattitude;
	Double sourceLongitude;
	Double destinationLongitude;
	
	public Long getRiderID() {
		return riderID;
	}
	public void setRiderID(Long riderID) {
		this.riderID = riderID;
	}
	public String getSourceLocation() {
		return sourceLocation;
	}
	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	public String getDestinationLocation() {
		return destinationLocation;
	}
	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}
	public Double getSourceLattitude() {
		return sourceLattitude;
	}
	public void setSourceLattitude(Double sourceLattitude) {
		this.sourceLattitude = sourceLattitude;
	}
	public Double getDestinationLattitude() {
		return destinationLattitude;
	}
	public void setDestinationLattitude(Double destinationLattitude) {
		this.destinationLattitude = destinationLattitude;
	}
	public Double getSourceLongitude() {
		return sourceLongitude;
	}
	public void setSourceLongitude(Double sourceLongitude) {
		this.sourceLongitude = sourceLongitude;
	}
	public Double getDestinationLongitude() {
		return destinationLongitude;
	}
	public void setDestinationLongitude(Double destinationLongitude) {
		this.destinationLongitude = destinationLongitude;
	}
	
}
