package com.woc.service;

import java.util.List;

import com.woc.entity.*;


public interface WOCService {

	public Iterable<User> getAllUsers();

	public Iterable<Pricing> getAllPrices();

	public Iterable<DriverAvailability> getDriverAvailabilityList();

	public Iterable<Feedback> getAllFeedbacks();

	public Iterable<Trip> getAllTrips();
	
	public List<Trip> getTrips();
	
	public Trip createTrip(Trip newTrip);

	public Iterable<UserLocation> getUserLocationList();

	public Iterable<Vehicle> getAllVehicles();
	
	
}
