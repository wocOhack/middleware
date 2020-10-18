package com.woc.service;

import java.net.URISyntaxException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woc.dto.Driver;
import com.woc.dto.DriverAvailability;
import com.woc.dto.DriverLocationUpdateRequest;
import com.woc.dto.DriverSearchCriteria;
import com.woc.dto.DrivingLicense;
import com.woc.dto.EndRideRequestDto;
import com.woc.dto.EndRideResponseDto;
import com.woc.dto.FeedbackDto;
import com.woc.dto.RideRequestUpdateObject;
import com.woc.dto.StartRideRequestDto;
import com.woc.dto.Vehicle;
import com.woc.entity.Feedback;
import com.woc.entity.Pricing;
import com.woc.entity.RideRequest;
import com.woc.entity.Trip;
import com.woc.entity.User;
import com.woc.entity.UserCredentials;
import com.woc.repository.DriverAvailabilityRepository;
import com.woc.repository.DriverRepository;
import com.woc.repository.FeedbackRepository;
import com.woc.repository.PricingRepository;
import com.woc.repository.RideRequestRepository;
import com.woc.repository.RiderRepository;
import com.woc.repository.TripRepository;
import com.woc.repository.UserCredentialsRepository;
import com.woc.repository.UserRepository;
import com.woc.repository.VehicleRepository;
import com.woc.service.enums.PushNotificationIdentifierEnum;
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
    
    private static String DRIVER_STATUS_AVAILABLE="Available";
    
    private static String IN_TRIP="InTrip";

    public void toggleDriverAvailability(long user_id, String status) {
        driverAvailabilityRepository.toggleDriverAvailability(user_id, status);
    }

    public long addDriver(com.woc.dto.Driver driver, Vehicle vehcile, DrivingLicense license) {

        com.woc.entity.Driver d = new com.woc.entity.Driver();
        com.woc.entity.Vehicle v = new com.woc.entity.Vehicle();
        UserCredentials cred = new UserCredentials();
        User u = new User();

        DriverSearchCriteria search = new DriverSearchCriteria();
        if (driver == null || license == null) {
            return -1l;
        }
        if (driver.getPhoneNumber() == null || driver.getPhoneNumber().trim().isEmpty()) {
            return -1l;
        }
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
        v.setInsuranceNumber(vehcile.getInsuranceNumber());
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

    public Driver updateDriver(Driver driver, Vehicle vehcile, DrivingLicense license) {
        long idUpdated = 0l;

        
        // returning -1 for badrequest mandate paramater missing
        if (driver == null ){
            return null;
        }
        if (driver.getDriverID() == 0
                && (driver.getPhoneNumber().trim().isEmpty() || driver.getPhoneNumber() == null)) {
            return null;
        }

        Driver d = new Driver();
        DriverSearchCriteria search = new DriverSearchCriteria();

        if (driver.getDriverID() != 0) {
            search.setDriverID(driver.getDriverID());
        } else {
            search.setPhoneNumber(driver.getPhoneNumber());
        }

        d = driverRepository.getDriver(search);
        System.out.println("Driver Found to be updated - " + d.getDriverID());
        long userId = d.getUserID();
        long user_update = 0l;
        if ((driver.getEmail() != null && !driver.getEmail().trim().isEmpty())
                && (driver.getName() != null && !driver.getName().trim().isEmpty())) {
            System.out.println("Gonna update driver with email and name");
            user_update = userRepository.updateUser(driver.getName(), driver.getEmail(), driver.getPhoneNumber(),
                    d.getUserID());
            d.setEmail(driver.getEmail());
            d.setName(driver.getName());
        } else if (driver.getEmail() != null && !driver.getEmail().trim().isEmpty()) {
            System.out.println("Gonna update rider with email alone ");
            
            user_update = userRepository.updateUser("", driver.getEmail(), driver.getPhoneNumber(), d.getUserID());
            d.setEmail(driver.getEmail());
            // if (user_update != 0) {
            // long updated = riderRepository.updateRiderData(rider);
            // return updated;
            // } else {
            // return 0l;
            // }
            // return user_update;
        } else if (driver.getName() != null && !driver.getName().trim().isEmpty()) {
            user_update = userRepository.updateUser(driver.getName(), "", driver.getPhoneNumber(), d.getUserID());
            d.setName(driver.getName());
            // return user_update;
        }

        // if ((license.getLicenseDocumentLink() != null && !license.getLicenseDocumentLink().trim().isEmpty()) ||
        // driver.get) {
        if (license != null) {
            
            idUpdated = driverRepository.updateDriverData(driver, license);
        }

        // }
        if (idUpdated != 0 || user_update != 0) {
            
            return d;
        }

        return new Driver();
    }

    public void notifyNearestDrivers(String riderLocation, long rideRequestID) throws URISyntaxException {
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
        List<String> androidIds = new ArrayList<>();
        int numOfNearestDrivers = nearestDrivers.size() > 5 ? 5 : nearestDrivers.size();
        for (int i = 0; i < numOfNearestDrivers; i++) {
            androidIds.add(nearestDrivers.get(i).getDeviceID());
            notifiedDrivers.add(nearestDrivers.get(i).getDriverID());

        }

        Map<String, Object> notificationPayload = new HashMap<>();
        notificationPayload.put("rideRequestId", rideRequestID);
        //pushNotificationService.send(PushNotificationIdentifierEnum.RIDE_REQUEST_FOUND, notificationPayload,
         //       androidIds);

        driverRepository.updateDriversStatus("Notified", notifiedDrivers);
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
        driver.setDeviceID(driverEntity.getDeviceID());
        driver.setDistanceFromRider(
                locationService.getDistanceBetweenLocations(driverEntity.getLocation(), riderLocation));
        return driver;
    }

    public void acceptRideRequest(long driverID, RideRequest rideRequest) throws URISyntaxException {

        com.woc.entity.Driver driver = driverRepository.findByID(driverID);
        rideRequest.setDriverId(driver);
        rideRequestRepository.updateRideRequest(rideRequest);

        User driverDetails = userRepository.findByID(driver.getUser_id());

        Map<String, Object> notificationPayload = new HashMap<>();
        notificationPayload.put("rideRequestId", rideRequest.getId());
        notificationPayload.put("driverName", driverDetails.getName());
        notificationPayload.put("rating", driverDetails.getRating());
        notificationPayload.put("vehicle", vehicleRepository.findVehicleByUserId(driverDetails.getId()));
        notificationPayload.put("phone", driverDetails.getPhone());
        notificationPayload.put("liveLocation", driver.getLocation());

        List<String> riderAndroidId = new ArrayList<>();
        riderAndroidId.add(rideRequest.getRiderId().getDeviceID());

        pushNotificationService.send(PushNotificationIdentifierEnum.DRIVER_ENROUTE, notificationPayload,
                riderAndroidId);
        List<Long> driveridsForUpdate = new ArrayList<Long>();
    	driveridsForUpdate.add(driverID);
        driverRepository.updateDriversStatus(IN_TRIP,driveridsForUpdate);
        List<Long> notifiedDrivers = getNotifiedDriverIDs(rideRequest.getNotifiedDrivers().split(","));
        notifiedDrivers.remove(driverID);
        driverRepository.updateDriversStatus(DRIVER_STATUS_AVAILABLE,notifiedDrivers);
    }

    public void rejectRideRequest(long driverID, RideRequest rideRequest){
        
    	List<Long> driveridsForUpdate = new ArrayList<Long>();
    	driveridsForUpdate.add(driverID);		
        driverRepository.updateDriversStatus(DRIVER_STATUS_AVAILABLE,driveridsForUpdate);
    }

    public long updateDriverLocation(DriverLocationUpdateRequest request) {
        return driverRepository.updateDriverLocation(request);
    }

    public int submitFeedback(FeedbackDto feedbackDTO) throws Exception {

        Feedback feedback = new Feedback();
        Trip trip = tripRepository.findTripById(feedbackDTO.getTripId());

        if (trip == null) {
            return -1;
        }

        if (!trip.getStatus().equals(TripStatusEnum.TRIP_ENDED.toString())) {
            return -2;
        }

        User riderProfile = userRepository.findByID(riderRepository.findByID(trip.getRiderId()).getUser_id());
        User driverProfile = userRepository.findByID(driverRepository.findByID(trip.getDriverId()).getUser_id());

        boolean feedbackAlreadyExists = feedbackRepository.doesFeedbackAlreadyExist(trip.getId(),
                driverProfile.getId());
        if (feedbackAlreadyExists) {
            return 1;
        }

        Long count = feedbackRepository.getFeedbackCountForUser(riderProfile.getId());
        Double updateRating = count == 0 ? feedbackDTO.getRating()
                : ((riderProfile.getRating() * count + feedbackDTO.getRating()) / (count + 1));
        userRepository.updateUserRating(updateRating, riderProfile.getId());

        feedback.setTripId(trip.getId());
        feedback.setUserId(riderProfile.getId());
        feedback.setFeedbackOwnerId(driverProfile.getId());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setComment(feedbackDTO.getComments());

        feedbackRepository.submitFeedback(feedback);
        return 1;
    }

    public Long startRide(StartRideRequestDto startRideRequestDto) throws Exception {

        RideRequest rideRequest = rideRequestRepository.findById(startRideRequestDto.getRideRequestId());

        if (rideRequest == null || rideRequest.getDriverId() == null || rideRequest.getRiderId() == null
                || rideRequest.getStartLocation() == null || rideRequest.getEndLocation() == null) {
            return -1l;
        }

        if (!rideRequest.getRiderId().getPin().equals(startRideRequestDto.getPin())) {
            return -2l;
        }

        List<String> deviceIdListForPushNotif = new ArrayList<String>();
        deviceIdListForPushNotif.add(rideRequest.getRiderId().getDeviceID());

        Long ongoingTripId = tripRepository.isTripInProgress(rideRequest.getDriverId().getId(),
                rideRequest.getRiderId().getId());
        if (ongoingTripId != 0l) {
            pushNotificationService.send(PushNotificationIdentifierEnum.TRIP_START, null, deviceIdListForPushNotif);
            return ongoingTripId;
        }

        Trip trip = new Trip();
        trip.setRiderId(rideRequest.getRiderId().getId());
        trip.setDriverId(rideRequest.getDriverId().getId());
        trip.setStartLocation(rideRequest.getStartLocation());
        trip.setEndLocation(rideRequest.getEndLocation());

        trip.setTripStartTime(new Date());
        trip.setDistance(locationService.getDistanceBetweenLocations(rideRequest.getStartLat() + ":" + rideRequest.getStartLong(),
                rideRequest.getEndLat() + ":" + rideRequest.getEndLong()));
        trip.setStatus(TripStatusEnum.TRIP_IN_PROGRESS.toString());
        trip.setCreatedTime(trip.getTripStartTime());
        trip.setUpdatedTime(trip.getTripStartTime());

        Trip persistedTrip = tripRepository.createTrip(trip);

        pushNotificationService.send(PushNotificationIdentifierEnum.TRIP_START, null, deviceIdListForPushNotif);

        rideRequestRepository.deleteRideRequest(rideRequest);

        return persistedTrip.getId();
    }

    public EndRideResponseDto endRide(EndRideRequestDto endRideRequestDto) throws URISyntaxException {

        EndRideResponseDto endRideResponseDto = new EndRideResponseDto();
        Trip trip = tripRepository.findTripById(endRideRequestDto.getTripId());

        if (trip == null) {
            return null;
        }

        List<String> riderAndroidId = new ArrayList<String>();
        riderAndroidId.add(riderRepository.findByID(trip.getRiderId()).getDeviceID());

        if (trip.getStatus().equals(TripStatusEnum.TRIP_ENDED.toString())) {
            endRideResponseDto.setPickup(trip.getStartLocation());
            endRideResponseDto.setDestination(trip.getEndLocation());
            endRideResponseDto.setCity(endRideRequestDto.getCity());
            endRideResponseDto.setDistance(endRideRequestDto.getDistanceStr());
            endRideResponseDto.setDuration(endRideRequestDto.getDurationStr());
            endRideResponseDto.setFare(trip.getCost().toString());

            Map<String, Object> notificationPayload = new HashMap<String, Object>();
            notificationPayload.put("duration", endRideRequestDto.getDurationStr());
            notificationPayload.put("distance", endRideRequestDto.getDistanceStr());
            notificationPayload.put("fare", trip.getCost());

            pushNotificationService.send(PushNotificationIdentifierEnum.TRIP_END, notificationPayload, riderAndroidId);
            return endRideResponseDto;
        }

        if (!trip.getStatus().equals(TripStatusEnum.TRIP_IN_PROGRESS.toString())) {
            return null;
        }

        Pricing pricing = pricingRepository.getPricingByName(endRideRequestDto.getCity());
        if (pricing == null) {
            return null;
        }

        Double fare = (pricing.getCostPerKm() * (endRideRequestDto.getDistanceInMeters() / 1000.00))
                + (pricing.getCostPerMin() * (endRideRequestDto.getDurationInSecs() / 60.00)) + pricing.getExtraCharges();
        if (endRideRequestDto.getPickup() != null) {
            trip.setStartLocation(endRideRequestDto.getPickup());
        }
        if (endRideRequestDto.getDestination() != null) {
            trip.setEndLocation(endRideRequestDto.getDestination());
        }
        if (endRideRequestDto.getDistanceInMeters() != null) {
            trip.setDistance(endRideRequestDto.getDistanceInMeters());
        }
        trip.setDuration(endRideRequestDto.getDurationInSecs());
        trip.setTripEndTime(new Date(trip.getTripStartTime().getTime() + (endRideRequestDto.getDurationInSecs() * 1000)));
        trip.setCost(fare);
        trip.setStatus(TripStatusEnum.TRIP_ENDED.toString());
        trip.setUpdatedTime(new Date());

        Trip persistedTrip = tripRepository.updateTrip(trip);

        endRideResponseDto.setCity(endRideRequestDto.getCity());
        endRideResponseDto.setPickup(persistedTrip.getStartLocation());
        endRideResponseDto.setDestination(persistedTrip.getEndLocation());
        endRideResponseDto.setDistance(endRideRequestDto.getDistanceStr());
        endRideResponseDto.setDuration(endRideRequestDto.getDurationStr());
        endRideResponseDto.setFare(persistedTrip.getCost().toString());

        Map<String, Object> notificationPayload = new HashMap<String, Object>();
        notificationPayload.put("duration", endRideRequestDto.getDurationStr());
        notificationPayload.put("distance", endRideRequestDto.getDistanceStr());
        notificationPayload.put("fare", persistedTrip.getCost());

        pushNotificationService.send(PushNotificationIdentifierEnum.TRIP_END, notificationPayload, riderAndroidId);

        List<Long> driverId = new ArrayList<>();
        driverId.add(persistedTrip.getDriverId());
        driverRepository.updateDriversStatus("Available", driverId);

        return endRideResponseDto;
    }
    
    private List<Long> getNotifiedDriverIDs(String[] notifiedDriverIDsString) {
    	
    	List<Long> notifiedDriverIDs = new ArrayList<Long>();
    	for(String id: notifiedDriverIDsString) {
    		notifiedDriverIDs.add(Long.parseLong(id));
    	}
    	return notifiedDriverIDs;
    }
}
