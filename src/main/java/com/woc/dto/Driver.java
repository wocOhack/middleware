package com.woc.dto;

import java.io.Serializable;
import java.util.Map;

public class Driver implements Serializable{

	private static final long serialVersionUID = -812783202505901774L;
	private String name;
	private long driverID;
	private long userID;
	private String phoneNumber;
	private String email;
	private Map<String,String> documents;
	private String PIN;
	private String status;
	private double distanceFromRider;
	private String deviceID;
	private String address;
	
	
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
	public double getDistanceFromRider() {
		return distanceFromRider;
	}
	public void setDistanceFromRider(double distance) {
		this.distanceFromRider = distance;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
    public long getUserID() {
        return userID;
    }
    public void setUserID(long userID) {
        this.userID = userID;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    
}
