package com.woc.service;

import java.util.List;

import com.woc.entity.*;
import com.woc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WOCServiceImpl implements WOCService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PricingRepository pricingRepository;

	@Autowired
	DriverAvailabilityRepository driverAvailabilityRepository;

	@Autowired
	FeedbackRepository feedbackRepository;

	@Autowired
	TripRepository tripRepository;

	@Autowired
	UserLocationRepository userLocationRepository;

	@Autowired
	VehicleRepository vehicleRepository;


	public Iterable<User> getAllUsers(){
		System.out.println("Reached");
		return userRepository.findAll();
	}
	

	public Iterable<Pricing> getAllPrices(){
		System.out.println("Reached");
		return pricingRepository.findAll();
	}

	public Iterable<DriverAvailability> getDriverAvailabilityList() {
		System.out.println("Reached");
		return driverAvailabilityRepository.findAll();
	}

	public Iterable<Feedback> getAllFeedbacks() {
		System.out.println("Reached");
		return feedbackRepository.findAll();
	}

	public Iterable<Trip> getAllTrips() {
		System.out.println("Reached");
		return tripRepository.findAll();
	}

	public Iterable<UserLocation> getUserLocationList() {
		System.out.println("Reached");
		return userLocationRepository.findAll();
	}

	public Iterable<Vehicle> getAllVehicles() {
		System.out.println("Reached");
		return vehicleRepository.findAll();
	}
}
