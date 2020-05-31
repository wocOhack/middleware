package com.woc.controller;
import com.woc.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.woc.service.WOCService;


@RestController  
@EnableAutoConfiguration
@RequestMapping("/woc")
public class MainController {
		
	@Autowired
	WOCService wocService;

	@GetMapping("/greet")
	@ResponseBody
	public String hello() {
		return "Greetings!";
	}
	
	@GetMapping("/users")
	@ResponseBody
	public Iterable<User> getUsers() {
		return wocService.getAllUsers();
	}
	
	@GetMapping("/prices")
	@ResponseBody
	public Iterable<Pricing> getPrices(){
		return wocService.getAllPrices();
	}

	@GetMapping("/available-drivers")
	@ResponseBody
	public Iterable<DriverAvailability> getDriverAvailabilityList() { return wocService.getDriverAvailabilityList(); }

	@GetMapping("/feedbacks")
	@ResponseBody
	public Iterable<Feedback> getFeedbacks() { return wocService.getAllFeedbacks(); }

	@GetMapping("/trips")
	@ResponseBody
	public Iterable<Trip> getTrips() { return wocService.getAllTrips(); }
	
	@PostMapping("/trips")
	@ResponseBody
	public Iterable<Trip> createTrip() { return wocService.getAllTrips(); }

	@GetMapping("/user-locations")
	@ResponseBody
	public Iterable<UserLocation> getUserLocationList() { return wocService.getUserLocationList(); }

	@GetMapping("/vehicles")
	@ResponseBody
	public Iterable<Vehicle> getVehicles() { return wocService.getAllVehicles(); }
	
}