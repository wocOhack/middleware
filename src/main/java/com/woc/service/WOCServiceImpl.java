package com.woc.service;

import java.util.List;

import com.woc.entity.*;
import com.woc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woc.entity.Driver;
import com.woc.entity.Pricing;
import com.woc.entity.Rider;
import com.woc.entity.User;
import com.woc.repository.DriverRepository;
import com.woc.repository.PricingRepository;
import com.woc.repository.RiderRepository;
import com.woc.repository.UserRepository;

@Service
public class WOCServiceImpl implements WOCService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PricingRepository pricingRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    RiderRepository riderRepository;

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


    @Override
    public Iterable<Rider> getAllRiders() {
        System.out.println("Reached");
        return riderRepository.findAll();
    }

    @Override
    public Iterable<Driver> getAllDrivers() {
        System.out.println("Reached");
        return driverRepository.findAll();
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


	@Override
	public List<Trip> getTrips() {
		return null;
	}


	@Override
	public Trip createTrip(Trip newTrip) {		
		return tripRepository.createTrip(newTrip);
	}
	
	public Trip updateTrip() {
		return null;
		
	}
    @Override
    public void addDriver(Driver driver) {
        System.out.println("Adding driver");
        driverRepository.addDriver(driver);
    }

    @Override
    public void addUser(User u) {
        System.out.println("Adding user");
        userRepository.addUser(u);
    }
    
    @Override
    public void addRider(Rider r) {
        System.out.println("Adding rider");
        riderRepository.addRider(r);
    }

}
