package com.woc.dto;

import java.io.Serializable;
import java.util.Map;

public class Driver implements Serializable{

	private static final long serialVersionUID = -812783202505901774L;
	private String name;
	private long driverID;
	private String phoneNumber;
	private String email;
	private Map<String,String> documents;
	private String PIN;
	private String status;
	private int distanceFromRider;
	private String deviceID;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public long getDriverID() {
		return driverID;
	}
	public void setDriverID(long driverID) {
		this.driverID = driverID;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Map<String, String> getDocuments() {
		return documents;
	}
	public void setDocuments(Map<String, String> documents) {
		this.documents = documents;
	}
	public String getPIN() {
		return PIN;
	}
	public void setPIN(String pIN) {
		PIN = pIN;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getDistanceFromRider() {
		return distanceFromRider;
	}
	public void setDistanceFromRider(int distance) {
		this.distanceFromRider = distance;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	
}
