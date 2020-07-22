package com.woc.dto;

public class DriverRegistrationRequest {

	private Driver driver;
	private DrivingLicense insurance;
	private Vehicle vehicle;
	
	
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public DrivingLicense getInsurance() {
		return insurance;
	}
	public void setInsurance(DrivingLicense insurance) {
		this.insurance = insurance;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	
}
