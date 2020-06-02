package com.woc.service;

import java.util.List;

import com.woc.entity.*;
import com.woc.entity.Driver;
import com.woc.entity.Pricing;
import com.woc.entity.Rider;
import com.woc.entity.User;

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

    public Iterable<Rider> getAllRiders();

    Iterable<Driver> getAllDrivers();

    void addDriver(Driver driver);

    void addUser(User u);
    
    void addRider(Rider r);
}
