package com.woc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woc.dto.Ride;
import com.woc.dto.Rider;
import com.woc.dto.Trip;
import com.woc.dto.TripSearchCriteria;
import com.woc.service.RiderService;

@RestController
@RequestMapping("/woc/rider")
public class RiderController {

	@Autowired
	RiderService riderService;
	
	@PostMapping("/createProfile")
	public long createNewUser(@RequestBody Rider newRider) {
		return 1L;
	}
	
	@PutMapping("/updateProfile")
	public void updateProfile(@RequestBody Rider rider) {
		return;
	}
	
	@GetMapping("/getProfile")
	public Rider getProfile(@RequestBody Rider rider) {
		
		return rider;
	}
	
	@PutMapping("/updatePIN")
	public void updatePIN(@RequestBody Rider rider) {
		return;
	}
	
	@PostMapping("/requestRide")
	public void requestRide(@RequestBody Ride ride) {
		return;
	}
	
	@PostMapping("/cancelRide")
	public void cancelRide(@RequestBody Ride ride) {
		return;
	}
	
	@GetMapping("getTrips")
	public List<Trip> getTrips(@RequestBody TripSearchCriteria criteria){
		
		List<Trip> trips = new ArrayList<Trip>();
		return trips;
		
	}
	
	
}
