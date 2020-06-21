package com.woc.dto;

import java.util.Date;

public class DrivingLicense {

	private String licenceNumber;
	private Date  expiryDate;
	private String licenseDocumentLink;
	public String getLicenceNumber() {
		return licenceNumber;
	}
	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getLicenseDocumentLink() {
		return licenseDocumentLink;
	}
	public void setLicenseDocumentLink(String licenseDocumentLink) {
		this.licenseDocumentLink = licenseDocumentLink;
	}
	
}
