package com.woc.service;

import com.woc.entity.*;


public interface WOCService {

	Iterable<User> getAllUsers();

	Iterable<Pricing> getAllPrices();

	Iterable<DriverAvailability> getDriverAvailabilityList();

	Iterable<Feedback> getAllFeedbacks();

	Iterable<Trip> getAllTrips();

	Iterable<UserLocation> getUserLocationList();

	Iterable<Vehicle> getAllVehicles();
}
