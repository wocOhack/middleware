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
		return userRepository.findAll();
	}
	

	public Iterable<Pricing> getAllPrices(){
		return pricingRepository.findAll();
	}

	public Iterable<DriverAvailability> getDriverAvailabilityList() {
		return driverAvailabilityRepository.findAll();
	}

	public Iterable<Feedback> getAllFeedbacks() {
		return feedbackRepository.findAll();
	}

	public Iterable<Trip> getAllTrips() {
		return tripRepository.findAll();
	}

	public Iterable<UserLocation> getUserLocationList() {
		return userLocationRepository.findAll();
	}

	public Iterable<Vehicle> getAllVehicles() {
		return vehicleRepository.findAll();
	}
}
