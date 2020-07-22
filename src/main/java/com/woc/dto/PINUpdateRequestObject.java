package com.woc.dto;

import java.io.Serializable;

public class PINUpdateRequestObject implements Serializable{

	private static final long serialVersionUID = 2761918252146493896L;
	private long riderID;
	private String PIN;
	
	
	public long getRiderID() {
		return riderID;
	}
	public void setRiderID(Long riderID) {
		this.riderID = riderID;
	}
	public String getPIN() {
		return PIN;
	}
	public void setPIN(String pIN) {
		PIN = pIN;
	}
	
}
