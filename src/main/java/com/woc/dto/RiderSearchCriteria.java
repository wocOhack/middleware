package com.woc.dto;

import java.io.Serializable;

public class RiderSearchCriteria implements Serializable{

	private static final long serialVersionUID = -6779314507526973982L;
	
	private String phoneNumber;
	private String riderId;
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRiderId() {
		return riderId;
	}
	public void setRiderId(String riderId) {
		this.riderId = riderId;
	}

}
