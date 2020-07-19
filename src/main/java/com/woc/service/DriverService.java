package com.woc.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woc.dto.Driver;
import com.woc.dto.DriverAvailability;
import com.woc.dto.DriverSearchCriteria;
import com.woc.dto.DrivingLicense;
import com.woc.dto.RideRequestUpdateObject;
import com.woc.dto.Vehicle;
import com.woc.entity.RideRequest;
import com.woc.entity.User;
import com.woc.entity.UserCredentials;
import com.woc.repository.DriverAvailabilityRepository;
import com.woc.repository.DriverRepository;
import com.woc.repository.RideRequestRepository;
import com.woc.repository.UserCredentialsRepository;
import com.woc.repository.UserRepository;
import com.woc.repository.VehicleRepository;

@Component
public class DriverService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	DriverAvailabilityRepository driverAvailabilityRepository;

	@Autowired
	DriverRepository driverRepository;

	@Autowired
	VehicleRepository vehicleRepository;

	@Autowired
	UserCredentialsRepository userCredRepository;

	@Autowired
	LocationService locationService;

	@Autowired
	PushNotificationService notificationService;

	@Autowired
	RideRequestRepository rideRequestRepository;

	public void toggleDriverAvailability(long user_id, boolean status) {
		driverAvailabilityRepository.toggleDriverAvailability(user_id, status);
	}

	public long addDriver(Driver driver, Vehicle vehcile, DrivingLicense license) {

		com.woc.entity.Driver d = new com.woc.entity.Driver();
		com.woc.entity.Vehicle v = new com.woc.entity.Vehicle();
		UserCredentials cred = new UserCredentials();
		User u = new User();

		DriverSearchCriteria search = new DriverSearchCriteria();
		search.setPhoneNumber(driver.getPhoneNumber());
		Driver ifexisting = driverRepository.getDriver(search);
		if (ifexisting != null) {
			// send 400 bad request as user already exist....
			System.out.println("User already exist.....");
			return -1;
		}
		Date now = new Date(System.currentTimeMillis());
		// int userId = Math.abs(new Random().nextInt());
		// int driverId = Math.abs(new Random().nextInt());
		u.setEmail(driver.getEmail());
		// u.setId((long) userId);
		u.setName(driver.getName());
		u.setPhone(driver.getPhoneNumber());
		u.setRegistrationDate(now);
		u.setType("DRIVER");
		// System.out.println("userId : " + userId);
		// System.out.println("driverId : " + driverId);
		User createdUser = userRepository.addUser(u);

		System.out.println("userId : " + createdUser.getId());

		d.setVerified_by("Admin");
		d.setVerification_date(now);
		d.setLcense_number(license.getLicenceNumber());
		d.setLicense_doc(license.getLicenseDocumentLink());
		d.setLicense_expiry_date(license.getExpiryDate());
		d.setIs_verified(true);
		d.setAddress("NA");
		// d.setId((long) driverId);
		d.setUser_id(createdUser.getId());

		com.woc.entity.Driver createdDriver = driverRepository.addDriver(d);
		System.out.println("driverId : " + createdDriver.getId());

		v.setInsuranceDoc(vehcile.getInsuranceDocumentLink());
		v.setVehicleDoc(vehcile.getRCDocumentLink());
		v.setIsVerified(true);
		v.setVehicleNumber(vehcile.getRegistrationNumber());
		v.setVehicleType("4-Wheeler");
		v.setUser(createdUser.getId());
		vehicleRepository.addVehcile(v);

		cred.setUser_name(driver.getName());
		cred.setUser_pin(driver.getPIN());
		cred.setUser_id(createdUser.getId());
		userCredRepository.addUserCredentails(cred);
		return createdDriver.getId();

	}

	public Driver getDriver(DriverSearchCriteria search) {
		Driver d = driverRepository.getDriver(search);
		return d;
	}

	public long toggleDriverAvailability(DriverAvailability availability) {
		long result = driverAvailabilityRepository.toggleDriverAvailability(availability.getDriverID(),
				availability.getStatus());
		return result;
	}

	public List<com.woc.entity.Driver> getAllAvailableDrivers() {

		List<com.woc.entity.Driver> availableDrivers = driverRepository.getAllDriversWithStatus("Available");
		return availableDrivers;
	}

	public void notifyNearestDrivers(String riderLocation, String destinationLocation, long rideRequestID) {

		List<com.woc.entity.Driver> availableDrivers = getAllAvailableDrivers();
		List<Long> notifiedDrivers = new ArrayList<Long>();

		List<Driver> nearestDrivers = new ArrayList<Driver>();

		for (com.woc.entity.Driver driver : availableDrivers) {
			Driver driverWithDistance = getDriverWithDistance(driver, riderLocation);
			/**
			 * if(driverWithDistance.getDistanceFromRider() <=5) {
			 * 
			 * }
			 **/
			nearestDrivers.add(driverWithDistance);
		}
		Collections.sort(nearestDrivers, new Comparator<Driver>() {

			@Override
			public int compare(Driver o1, Driver o2) {
				if (o1.getDistanceFromRider() > o2.getDistanceFromRider()) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		StringBuffer deviceIDs = new StringBuffer("");
		for (int i = 0; i < 5; i++) {
			deviceIDs.append(nearestDrivers.get(i).getDeviceID()).append(",");
			notifiedDrivers.add(nearestDrivers.get(i).getDriverID());

		}
		StringBuffer message = new StringBuffer("{Ride Offer: {rideRequestID:");
		message.append(rideRequestID).append("}}");
		notificationService.send("{Ride Offer: {rideRequestID:", deviceIDs.substring(0, deviceIDs.length() - 1));
		updateDriversStatus(notifiedDrivers, "Blocked");
		updateRideRequestWithNotifiedDrivers(notifiedDrivers,rideRequestID);
	}

	public void updateRideRequestWithNotifiedDrivers(List<Long> notifiedDrivers, long rideRequestID) {

		RideRequest rideRequest = rideRequestRepository.findById(rideRequestID);
		StringBuffer notifiedDriverIds = new StringBuffer("");
		for (long driverid : notifiedDrivers) {
			notifiedDriverIds.append(driverid).append(",");
		}
		rideRequest.setNotifiedDrivers(notifiedDriverIds.substring(0, notifiedDriverIds.length() - 1));
		rideRequestRepository.updateRideRequest(rideRequest);

	}

	public Driver getDriverWithDistance(com.woc.entity.Driver driverEntity, String riderLocation) {

		Driver driver = new Driver();
		driver.setDriverID(driverEntity.getId());
		driver.setDistanceFromRider(
				locationService.getDistanceBetweenLocations(driverEntity.getLocation(), riderLocation));
		return driver;
	}

	public void acceptRideRequest(RideRequestUpdateObject requestObject, RideRequest rideRequest) {

		com.woc.entity.Driver driver = driverRepository.findByID(requestObject.getDriverID());
		rideRequest.setDriverId(driver);
		rideRequestRepository.updateRideRequest(rideRequest);
		//To do send driver profile information
		notificationService.send("", Long.toString(rideRequest.getRiderId().getDeviceID()));
	}

	public void updateDriversStatus(List<Long> driverIds, String status) {
		// bulk update drivers with status
	}
}
