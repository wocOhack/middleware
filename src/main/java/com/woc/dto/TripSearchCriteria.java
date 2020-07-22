package com.woc.dto;

import java.io.Serializable;
import java.util.Date;

public class TripSearchCriteria implements Serializable{

	private static final long serialVersionUID = -5468719734560050560L;
	private Long tripID;
	private Long riderID;
	private Long driverID;
	private Date beforeTime;
	private Date afterTime;
	public Long getTripID() {
		return tripID;
	}
	public void setTripID(Long tripID) {
		this.tripID = tripID;
	}
	public Long getRiderID() {
		return riderID;
	}
	public void setRiderID(Long riderID) {
		this.riderID = riderID;
	}
	public Long getDriverID() {
		return driverID;
	}
	public void setDriverID(Long driverID) {
		this.driverID = driverID;
	}
	public Date getBeforeTime() {
		return beforeTime;
	}
	public void setBeforeTime(Date beforeTime) {
		this.beforeTime = beforeTime;
	}
	public Date getAfterTime() {
		return afterTime;
	}
	public void setAfterTime(Date afterTime) {
		this.afterTime = afterTime;
	}
	
	
}
