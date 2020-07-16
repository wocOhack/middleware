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
import com.woc.dto.DriverLocationUpdateRequest;
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
import com.woc.dto.FeedBack;
import com.woc.entity.Feedback;
import com.woc.entity.Trip;
import com.woc.repository.FeedbackRepository;
import com.woc.repository.TripRepository;
import com.woc.service.enums.TripStatus;
import com.woc.service.exceptions.FeedbackSubmissionException;

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

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    TripRepository tripRepository;

    public void toggleDriverAvailability(long user_id, String status) {
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
            return -1l;
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
        if (availability.getDriverID() == 0
                && (availability.getStatus() == null || !availability.getStatus().trim().isEmpty())) {
            return 0l;
        }
        long result = driverRepository.updateDriverStatus(availability);
        return result;
    }

    public long updateDriver(Driver driver, Vehicle vehcile, DrivingLicense license) {
        long idUpdated = 0l;

        // returning -1 for badrequest mandate paramater missing
        if (driver.getDriverID() == 0
                && (driver.getPhoneNumber().trim().isEmpty() || driver.getPhoneNumber() == null)) {
            return 0l;
        }

        Driver d = new Driver();
        DriverSearchCriteria search = new DriverSearchCriteria();

        if (driver.getDriverID() != 0) {
            search.setDriverId(driver.getDriverID());
        } else {
            search.setPhoneNumber(driver.getPhoneNumber());
        }
        d = driverRepository.getDriver(search);
        long userId = d.getUserID();

        if (driver.getEmail() != null && !driver.getEmail().trim().isEmpty()) {
            long user_update = userRepository.updateUser("", driver.getEmail(), driver.getPhoneNumber(), userId);
            // if (user_update != 0) {
            // long updated = riderRepository.updateRiderData(rider);
            // return updated;
            // } else {
            // return 0l;
            // }
            // return user_update;
        }
        if (driver.getName() != null && !driver.getName().trim().isEmpty()) {
            long user_update = userRepository.updateUser(driver.getName(), "", driver.getPhoneNumber(), userId);
            // return user_update;
        }

        if (license.getLicenseDocumentLink() != null && !license.getLicenseDocumentLink().trim().isEmpty()) {
            return driverRepository.updateDriverData(driver, license);
        }
        return idUpdated;
    }

    public List<com.woc.entity.Driver> getAllAvailableDrivers() {

        List<com.woc.entity.Driver> availableDrivers = driverRepository.getAllDriversWithStatus("Available");
        return availableDrivers;
    }

    public void notifyNearestDrivers(String riderLocation, String destinationLocation, long rideRequestID) {

        List<com.woc.entity.Driver> availableDrivers = getAllAvailableDrivers();
        List<Driver> nearestDrivers = new ArrayList<Driver>();

        for (com.woc.entity.Driver driver : availableDrivers) {
            nearestDrivers.add(getDriverWithDistance(driver, riderLocation));
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

        for (int i = 0; i < 5; i++) {
            notificationService.send("", nearestDrivers.get(i).getDeviceID());
        }
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
        notificationService.send("", Long.toString(rideRequest.getRiderId().getDeviceID()));
    }

    public long updateDriverLocation(DriverLocationUpdateRequest request) {
        return driverRepository.updateDriverLocation(request);
    }

    public void submitFeedback(FeedBack feedbackDTO) throws FeedbackSubmissionException {
        Feedback feedback = new Feedback();

        Trip trip = tripRepository.findTripById(feedbackDTO.getTripId());

        if (trip == null) {
            throw new FeedbackSubmissionException("Bad Request. Could not find trip.");
        }

        if (!trip.getStatus().equals(TripStatus.TRIP_ENDED.toString())) {
            throw new FeedbackSubmissionException("Bad Request. This trip has not ended yet.");
        }

        List<Feedback> existingFeedbacksForTrip = feedbackRepository.getFeedbacksByTripId(trip.getId());
        if (existingFeedbacksForTrip != null) {
            for (Feedback f : existingFeedbacksForTrip) {
                if (f.getFeedbackOwnerId() == trip.getDriverId()) {
                    return;
                }
            }
        }
        feedback.setTripId(trip.getId());
        feedback.setUserId(trip.getRiderId());
        feedback.setFeedbackOwnerId(trip.getDriverId());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setComment(feedbackDTO.getComments());

        feedbackRepository.submitFeedback(feedback);
    }
}
