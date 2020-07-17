package com.woc.dto;

public class Vehicle {

	private String registrationNumber;
	private String insuranceNumber;
	private String RCDocumentLink;
	private String insuranceDocumentLink;


	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getInsuranceNumber() {
		return insuranceNumber;
	}
	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}
	public String getRCDocumentLink() {
		return RCDocumentLink;
	}
	public void setRCDocumentLink(String rCDocumentLink) {
		RCDocumentLink = rCDocumentLink;
	}
	public String getInsuranceDocumentLink() {
		return insuranceDocumentLink;
	}
	public void setInsuranceDocumentLink(String insuranceDocumentLink) {
		this.insuranceDocumentLink = insuranceDocumentLink;
	}


}
