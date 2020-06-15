package com.woc.dto;

import java.io.Serializable;
import java.util.Map;

public class Driver implements Serializable{

	private static final long serialVersionUID = -812783202505901774L;
	private String name;
	private long riderID;
	private String phoneNumber;
	private String email;
	private Map<String,String> documents;
	private String PIN;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getRiderID() {
		return riderID;
	}
	public void setRiderID(long riderID) {
		this.riderID = riderID;
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
	
	
	
}
