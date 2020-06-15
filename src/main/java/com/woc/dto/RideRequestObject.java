package com.woc.dto;

import java.io.Serializable;

public class RideRequestObject implements Serializable{

	private static final long serialVersionUID = -4622010308207657761L;

	Long riderID;
	String sourceLocation;
	String destinationLocation;
	
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
	
	
}
