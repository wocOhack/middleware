package com.woc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woc.dto.CancellRideRequestObject;
import com.woc.dto.RideRequestObject;
import com.woc.dto.RideRequestUpdateObject;
import com.woc.dto.PINUpdateRequestObject;
import com.woc.dto.FeedBack;
import com.woc.entity.Feedback;
import com.woc.entity.Trip;
import com.woc.repository.*;
import com.woc.service.enums.TripStatus;
import com.woc.service.exceptions.FeedbackSubmissionException;
import com.woc.dto.Rider;
import com.woc.dto.RiderSearchCriteria;
import com.woc.dto.TripSearchCriteria;
import com.woc.entity.RideRequest;
import com.woc.entity.ServiceableArea;
import com.woc.entity.User;
import com.woc.repository.DriverRepository;
import com.woc.repository.RideRequestRepository;
import com.woc.repository.RiderRepository;
import com.woc.repository.ServicableAreaRepository;
import com.woc.repository.TripRepository;
import com.woc.repository.UserRepository;
import com.woc.utilities.Utilities;

@Component
public class RiderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    ServicableAreaRepository servicableAreaRepository;

    @Autowired
    RideRequestRepository riderRequestsRepository;
    
    @Autowired
    DriverRepository driverRepository;

	@Autowired
	PushNotificationService notificationService;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    public ServiceableArea addArea(ServiceableArea area) {
        return servicableAreaRepository.addArea(area);
    }

    public Iterable<ServiceableArea> getAllAreas() {
        return servicableAreaRepository.findAll();
    }

    public long addRider(Rider rider) {
        System.out.println("Adding rider");
        Date now = new Date(System.currentTimeMillis());

        com.woc.entity.Rider r = new com.woc.entity.Rider();
        User u = new User();

        RiderSearchCriteria search = new RiderSearchCriteria();
        search.setPhoneNumber(rider.getPhoneNumber());
        Rider ifexisting = riderRepository.getRider(search);
        if (ifexisting != null) {
            // send 400 bad request as user already exist....
            System.out.println("User already exist.....");
            return -1;
        }
        u.setPhone(rider.getPhoneNumber());
        u.setEmail(rider.getEmail());
        u.setName(rider.getName());
        u.setType("RIDER");
        u.setRegistrationDate(now);

        userRepository.addUser(u);
        System.out.println("userId : " + u.getId());

        r.setIs_verified(true);
        r.setPin(rider.getPIN());
        // Map<String, String> documents = new HashMap<String, String>();
        if (rider.getDocuments() != null) {
            r.setProof_of_challenge(Utilities.convertWithStream(rider.getDocuments()));
        }
        r.setVerification_date(now);
        r.setUser_id(u.getId());
        r.setIs_challenged(rider.isDisabled());
        r.setDeviceID(rider.getDeviceID());
        return riderRepository.addRider(r);
    }

    public Rider getRider(RiderSearchCriteria search) {
        Rider rider = riderRepository.getRider(search);
        return rider;
    }

    public long createRideRequest(RideRequestObject rideRequest) {

        RideRequest request = new RideRequest();
        request.setRiderId(riderRepository.findByID(rideRequest.getRiderID()));
        request.setEndLocation(rideRequest.getDestinationLocation());
        request.setStartLocation(rideRequest.getSourceLocation());
        riderRequestsRepository.addRideRequest(request);
        return request.getId();
    }

    public void cancellRideRequest(CancellRideRequestObject request) {
	
    	com.woc.entity.Rider rider = riderRepository.findByID(request.getRiderID());
    	RideRequest rideRequest = riderRequestsRepository.findByRider(rider);
    	com.woc.entity.Driver driver= rideRequest.getDriverId();
    	List<Long> driverIds = new ArrayList<Long>();
    	driverIds.add(driver.getId());
    	driverRepository.updateDriversStatus("Available", driverIds);
    	notificationService.send("Ride has been cancelled by Rider", driver.getDeviceID());
    	riderRequestsRepository.deleteRideRequest(rideRequest);
    	return;
    }

    public RideRequest getRideRequest(RideRequestUpdateObject requestObject) {
        return riderRequestsRepository.findById(requestObject.getRideRequestID());
    }

    public long updateRider(Rider rider) {

        if ((rider.getRiderID() == 0l) && (rider.getPhoneNumber() == null || rider.getPhoneNumber().trim().isEmpty())) {
            return 0l;
        }

        Rider r = new Rider();
        RiderSearchCriteria search = new RiderSearchCriteria();
        if (rider.getRiderID() != 0l) {
            search.setRiderId(rider.getRiderID());
        } else {
            search.setPhoneNumber(rider.getPhoneNumber());
        }
        r = riderRepository.getRider(search);
        System.out.println("fetched Rider : " + r.getName() + r.getRiderID());
        System.out.println("rider.name : " + rider.getName() + rider.getRiderID());
        System.out.println("rider.address : " + rider.getEmail() + rider.getRiderID());
        System.out.println(r.getUserId());

        long user_update = 0l;
        if ((rider.getEmail() != null && !rider.getEmail().trim().isEmpty())
                && (rider.getName() != null && !rider.getName().trim().isEmpty())) {
            System.out.println("Gonna update rider with email and name");
            user_update = userRepository.updateUser(rider.getName(), rider.getEmail(), rider.getPhoneNumber(),
                    r.getUserId());
        } else if (rider.getEmail() != null && !rider.getEmail().trim().isEmpty()) {
            System.out.println("Gonna update rider with email alone ");

            user_update = userRepository.updateUser("", rider.getEmail(), rider.getPhoneNumber(), r.getUserId());
            // if (user_update != 0) {
            // long updated = riderRepository.updateRiderData(rider);
            // return updated;
            // } else {
            // return 0l;
            // }
            // return user_update;
        } else if (rider.getName() != null && !rider.getName().trim().isEmpty()) {
            user_update = userRepository.updateUser(rider.getName(), "", rider.getPhoneNumber(), r.getUserId());
            // return user_update;
        }

        if (rider.getDocuments() != null || (rider.getDeviceID()!=null && !rider.getDeviceID().trim().isEmpty())) {
            return riderRepository.updateRiderData(rider);
        }
        return user_update;
    }

    public long updateDriverPin(PINUpdateRequestObject request) {
        if ((request.getRiderID() == 0) || (request.getPIN() == null && request.getPIN().trim().isEmpty())) {
            return -1l;
        }
        return riderRepository.updateRiderPin(request);
    }

    public List<com.woc.dto.Trip> getRiderTrips(TripSearchCriteria searchCriteria) {
        List<com.woc.dto.Trip> fetchedTrips = new ArrayList<com.woc.dto.Trip>();
        List<com.woc.entity.Trip> trips = tripRepository.getTrips(searchCriteria);
        if (trips == null) {
            return fetchedTrips;
        }
        for (com.woc.entity.Trip each : trips) {
            com.woc.dto.Trip t = new com.woc.dto.Trip();
            t.setDistance(each.getDistance());
            t.setDuration(each.getDuration().getTime());
            t.setStartTime(each.getTripStartTime());
            t.setEndTime(each.getTripEndTime());
            t.setFare(each.getCost());

            t.setStartLocation(each.getStartLocation());
            t.setEndLocation(each.getEndLocation());
            fetchedTrips.add(t);
        }
        return fetchedTrips;
    }

    RiderService() {
        super();
    }

    public void submitFeedback(FeedBack feedbackDTO) throws FeedbackSubmissionException {
        Feedback feedback = new Feedback();

        Trip trip = tripRepository.findTripById(feedbackDTO.getTripId());

        if (trip == null) {
            throw new FeedbackSubmissionException("BAD REQUEST. Could not find trip.");
        }

        if (!trip.getStatus().equals(TripStatus.TRIP_ENDED.toString())) {
            throw new FeedbackSubmissionException("BAD REQUEST. This trip has not ended yet.");
        }

        List<Feedback> existingFeedbacksForTrip = feedbackRepository.getFeedbacksByTripId(trip.getId());
        if (existingFeedbacksForTrip != null) {
            for (Feedback f : existingFeedbacksForTrip) {
                if (f.getFeedbackOwnerId() == trip.getRiderId()) {
                    return;
                }
            }
        }

        feedback.setTripId(trip.getId());
        feedback.setUserId(trip.getDriverId());
        feedback.setFeedbackOwnerId(trip.getRiderId());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setComment(feedbackDTO.getComments());

        feedbackRepository.submitFeedback(feedback);
    }
}
