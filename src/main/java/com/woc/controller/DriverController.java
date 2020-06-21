package com.woc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woc.dto.Driver;
import com.woc.dto.DriverAvailability;
import com.woc.dto.DriverRegistrationRequest;
import com.woc.dto.DriverSearchCriteria;
import com.woc.dto.FeedBack;
import com.woc.dto.RideRequestUpdateObject;
import com.woc.dto.StartRideRequestObject;
import com.woc.dto.Trip;
import com.woc.service.DriverService;

@RestController
@RequestMapping("/woc/driver")
public class DriverController {

	//@Autowired
	DriverService driverService;
	
	@PostMapping("/createProfile")
	public long createNewDriver(@RequestBody DriverRegistrationRequest request) {
		return 2L;
	}
	
	@PutMapping("/updateProfile")
	public void updateDriverProfile(@RequestBody DriverRegistrationRequest reques) {
		return;
	}
	
	
	@GetMapping("/getProfile")
	public Driver getDriverProfile(@RequestBody DriverSearchCriteria searchCriteria) {
		Driver driver = new Driver();
		driver.setName("Ron Weisly");
		driver.setPhoneNumber("9876543210");
		return driver;
	}
	
	@PostMapping("/updateAvailability")
	public void updateDriverAvailability(@RequestBody DriverAvailability driverAvailability) {
		return;
	}
	
	@PostMapping("/updateRideRequest")
	public void updateRideRequest(@RequestBody RideRequestUpdateObject rideRequestUpdateObject) {
		return;
	}
	
	@PostMapping("/startRide")
	public void startRide(@RequestBody StartRideRequestObject startRideRequestObject) {
		return;
	}
	
	@PostMapping("/endRide")
	public Trip endRide(@RequestBody StartRideRequestObject startRideRequestObject) {
		Trip trip = new Trip();
		trip.setFare(200L);
		return trip;
	}
	
	@PostMapping("/submitFeedBack")
	public void submitFeedBack(@RequestBody FeedBack feedBack) {
		return;
	}
}
