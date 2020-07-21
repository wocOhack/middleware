package com.woc.service;

import java.util.*;

import com.woc.service.enums.PushNotificationIdentifierEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woc.entity.User;
import com.woc.entity.Trip;
import com.woc.entity.Feedback;
import com.woc.entity.UserCredentials;
import com.woc.entity.RideRequest;

import com.woc.dto.Driver;
import com.woc.dto.DriverAvailability;
import com.woc.dto.DriverLocationUpdateRequest;
import com.woc.dto.DriverSearchCriteria;
import com.woc.dto.DrivingLicense;
import com.woc.dto.RideRequestUpdateObject;
import com.woc.dto.Vehicle;
import com.woc.dto.FeedbackDto;
import com.woc.dto.StartRideRequestDto;

import com.woc.repository.DriverAvailabilityRepository;
import com.woc.repository.DriverRepository;
import com.woc.repository.RiderRepository;
import com.woc.repository.PricingRepository;
import com.woc.repository.RideRequestRepository;
import com.woc.repository.UserCredentialsRepository;
import com.woc.repository.UserRepository;
import com.woc.repository.VehicleRepository;
import com.woc.repository.FeedbackRepository;
import com.woc.repository.TripRepository;

import com.woc.service.enums.TripStatusEnum;

@Component
public class DriverService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DriverAvailabilityRepository driverAvailabilityRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserCredentialsRepository userCredRepository;

    @Autowired
    LocationService locationService;

    @Autowired
    RideRequestRepository rideRequestRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    PricingRepository pricingRepository;

    @Autowired
    PushNotificationService pushNotificationService;

    public void toggleDriverAvailability(long user_id, String status) {
        driverAvailabilityRepository.toggleDriverAvailability(user_id, status);
    }

    public long addDriver(com.woc.dto.Driver driver, Vehicle vehcile, DrivingLicense license) {

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

        d.setAddress(driver.getAddress());
        d.setStatus(driver.getStatus());
        d.setVerified_by("Admin");
        d.setVerification_date(now);
        d.setLcense_number(license.getLicenceNumber());
        d.setLicense_doc(license.getLicenseDocumentLink());
        d.setLicense_expiry_date(license.getExpiryDate());
        d.setIs_verified(true);
        d.setDeviceID(driver.getDeviceID());
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
            search.setDriverID(driver.getDriverID());
        } else {
            search.setPhoneNumber(driver.getPhoneNumber());
        }
        d = driverRepository.getDriver(search);
        long userId = d.getUserID();
        long user_update = 0l;
        if ((driver.getEmail() != null && !driver.getEmail().trim().isEmpty())
                && (driver.getName() != null && !driver.getName().trim().isEmpty())) {
            System.out.println("Gonna update rider with email and name");
             user_update = userRepository.updateUser(driver.getName(), driver.getEmail(), driver.getPhoneNumber(),
                   d.getUserID());
        } else if (driver.getEmail() != null && !driver.getEmail().trim().isEmpty()) {
            System.out.println("Gonna update rider with email alone ");

             user_update = userRepository.updateUser("", driver.getEmail(), driver.getPhoneNumber(), d.getUserID());
            // if (user_update != 0) {
            // long updated = riderRepository.updateRiderData(rider);
            // return updated;
            // } else {
            // return 0l;
            // }
            // return user_update;
        } else if (driver.getName() != null && !driver.getName().trim().isEmpty()) {
             user_update = userRepository.updateUser(driver.getName(), "", driver.getPhoneNumber(), d.getUserID());
            // return user_update;
        }
        
//        if ((license.getLicenseDocumentLink() != null && !license.getLicenseDocumentLink().trim().isEmpty()) || driver.get) {
          if (user_update !=0 && license != null) {
              idUpdated = driverRepository.updateDriverData(driver, license);
          }
         
//        }
        return idUpdated;
    }

    public void notifyNearestDrivers(String riderLocation, String destinationLocation, long rideRequestID) {

        List<com.woc.entity.Driver> availableDrivers = getAllAvailableDrivers();
        List<Driver> nearestDrivers = new ArrayList<Driver>();
        List<Long> notifiedDrivers = new ArrayList<Long>();


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

        StringBuffer deviceIDs = new StringBuffer("");
        for (int i = 0; i < 5; i++) {
            deviceIDs.append(nearestDrivers.get(i).getDeviceID()).append(",");
            notifiedDrivers.add(nearestDrivers.get(i).getDriverID());

        }
        StringBuffer message = new StringBuffer("{Ride Offer: {rideRequestID:");
        message.append(rideRequestID).append("}}");
        //pushNotificationService.send("{Ride Offer: {rideRequestID:", deviceIDs.substring(0, deviceIDs.length() - 1));
        driverRepository.updateDriversStatus("Blocked", notifiedDrivers);
        updateRideRequestWithNotifiedDrivers(notifiedDrivers,rideRequestID);
    }

    public List<com.woc.entity.Driver> getAllAvailableDrivers() {

        List<com.woc.entity.Driver> availableDrivers = driverRepository.getAllDriversWithStatus("Available");
        return availableDrivers;
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

        //pushNotificationService.send("","","", Long.toString(rideRequest.getRiderId().getDeviceID()));
    }


    public long updateDriverLocation(DriverLocationUpdateRequest request) {
        return driverRepository.updateDriverLocation(request);
    }

//    public void submitFeedback(FeedbackDto feedbackDTO) throws Exception {
//        Feedback feedback = new Feedback();
//
//        com.woc.entity.Trip trip = tripRepository.findTripById(feedbackDTO.getTripId());
//
//        if(trip == null) {
//            throw new Exception("NOT_FOUND");
//        }
//
//        if (!trip.getStatus().equals(TripStatusEnum.TRIP_ENDED.toString())) {
//            throw new Exception("BAD_REQUEST");
//        }
//
//        List<Feedback> existingFeedbacksForTrip = feedbackRepository.getFeedbacksByTripId(trip.getId());
//        if (existingFeedbacksForTrip != null) {
//            for (Feedback f : existingFeedbacksForTrip) {
//                if (f.getFeedbackOwnerId() == trip.getDriverId()) {
//                    return;
//                }
//            }
//        }
//        feedback.setTripId(trip.getId());
//        feedback.setUserId(trip.getRiderId());
//        feedback.setFeedbackOwnerId(trip.getDriverId());
//        feedback.setRating(feedbackDTO.getRating());
//        feedback.setComment(feedbackDTO.getComments());
//
//        feedbackRepository.submitFeedback(feedback);
//    }

    public Long startRide(StartRideRequestDto startRideRequestDto) throws Exception {

        RideRequest rideRequest = rideRequestRepository.findById(startRideRequestDto.getRideRequestId());

        if(rideRequest == null || rideRequest.getDriverId() == null || rideRequest.getRiderId() == null
            || rideRequest.getStartLocation() == null || rideRequest.getEndLocation() == null) {
            return -1l;
        }

        if(!rideRequest.getRiderId().getPin().equals(startRideRequestDto.getPin())) {
            return -2l;
        }

        List<String> deviceIdListForPushNotif = new ArrayList<String>();
        deviceIdListForPushNotif.add(rideRequest.getRiderId().getDeviceID());

        Long ongoingTripId = tripRepository.isTripInProgress(rideRequest.getDriverId().getId(), rideRequest.getRiderId().getId());
        if(ongoingTripId != 0l) {
            pushNotificationService.send(PushNotificationIdentifierEnum.TRIP_START, null, deviceIdListForPushNotif);
            return ongoingTripId;
        }

        Trip trip = new Trip();
        trip.setRiderId(rideRequest.getRiderId().getId());
        trip.setDriverId(rideRequest.getDriverId().getId());
        trip.setStartLocation(rideRequest.getStartLocation());
        trip.setEndLocation(rideRequest.getEndLocation());

        trip.setTripStartTime(new Date());
        trip.setDistance(locationService.getDistanceBetweenLocations(rideRequest.getStartLocation(), rideRequest.getEndLocation()));
        trip.setStatus(TripStatusEnum.TRIP_IN_PROGRESS.toString());
        trip.setCreatedTime(trip.getTripStartTime());
        trip.setUpdatedTime(trip.getTripStartTime());

        Trip persistedTrip = tripRepository.createTrip(trip);

        pushNotificationService.send(PushNotificationIdentifierEnum.TRIP_START, null, deviceIdListForPushNotif);

        return persistedTrip.getId();
    }

//    public TripDto endTrip(EndRideRequestObject endRideRequestObject) throw URISyntaxException {
//        com.woc.entity.Trip trip = tripRepository.findTripById(endRideRe);
//
//        if(trip == null) {
//
//        }
//
//        if(!trip.getStatus().equals(TripStatusEnum.TRIP_IN_PROGRESS.toString())) {
//
//        }
//
//        TripDto tripDto = new TripDto();
//        tripDto.setTripID(trip.getId());
//        tripDto.setStartTime(trip.getTripStartTime());
//        tripDto.setDistance(trip.getDistance());
//
//        RiderSearchCriteria riderSearchCriteria = new RiderSearchCriteria();
//        riderSearchCriteria.setRiderID(trip.getRiderId());
//        tripDto.setRider(riderRepository.getRider(riderSearchCriteria));
//
//        DriverSearchCriteria driverSearchCriteria = new DriverSearchCriteria();
//        driverSearchCriteria.setDriverID(trip.getDriverId());
//        tripDto.setDriver(driverRepository.getDriver(driverSearchCriteria));
//
//        if(tripDto.getDriver() == null || tripDto.getRider() == null) {
//
//        }
//
//        tripDto.setEndTime(new Date());
//        tripDto.setDuration(tripDto.getEndTime().getTime() - tripDto.getStartTime().getTime());
//
//        Double durationInMins = tripDto.getDuration()/60000.00;
//
//        //Pricing pricing = pricingRepository.getPricingById(endRideRequestObject.getCityId());
//        //Double fare = (pricing.getCostPerKm()*tripDto.getDistance()) + (pricing.getCostPerMin()*durationInMins) + (pricing.getExtraCharges());
//        //tripDto.setFare(fare);
//
//        //TODO: Update trip table with values
//
//        //TODO: send Push notif to rider
//        List<String> androidIds = new ArrayList<String>();
//        androidIds.add("xyz");
//        try {
//            pushNotificationService.send(END_TRIP_NOTIF_TITLE, END_TRIP_NOTIF_BODY, null, androidIds);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
}
