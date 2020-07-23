package com.woc.service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woc.dto.CancellRideRequestObject;
import com.woc.dto.FeedbackDto;
import com.woc.dto.PINUpdateRequestObject;
import com.woc.dto.RideRequestObject;
import com.woc.dto.RideRequestUpdateObject;
import com.woc.dto.Rider;
import com.woc.dto.RiderLocaionUpdateRequest;
import com.woc.dto.RiderSearchCriteria;
import com.woc.dto.TripDto;
import com.woc.dto.TripSearchCriteria;
import com.woc.entity.Feedback;
import com.woc.entity.RideRequest;
import com.woc.entity.ServiceableArea;
import com.woc.entity.Trip;
import com.woc.entity.User;
import com.woc.repository.DriverRepository;
import com.woc.repository.FeedbackRepository;
import com.woc.repository.RideRequestRepository;
import com.woc.repository.RiderRepository;
import com.woc.repository.ServicableAreaRepository;
import com.woc.repository.TripRepository;
import com.woc.repository.UserRepository;
import com.woc.service.enums.PushNotificationIdentifierEnum;
import com.woc.service.enums.TripStatusEnum;

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
    PushNotificationService pushNotificationService;

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

    public long updateRiderLocation(RiderLocaionUpdateRequest request) {
        return riderRepository.updateRiderLocation(request);
    }
    
    public Rider addRider(Rider rider) {
        System.out.println("Adding rider");
        Date now = new Date(System.currentTimeMillis());

        com.woc.entity.Rider r = new com.woc.entity.Rider();
        User u = new User();

        RiderSearchCriteria search = new RiderSearchCriteria();
        search.setPhoneNumber(rider.getPhoneNumber());
        Rider ifexisting = riderRepository.getRider(search);
        System.out.println("ifexisting : " + ifexisting);
        if (ifexisting != null) {
            // send 400 bad request as user already exist....
            System.out.println("User already exist.....");
            rider.setRiderID(-1);
            return rider;
            // return -1;
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
            if (rider.getDocuments().getDisabilityProof() != null && !rider.getDocuments().getDisabilityProof().isEmpty()) {
                r.setProof_of_challenge(rider.getDocuments().getDisabilityProof());
            }
          
        }
        r.setLocation(rider.getLocation());
        r.setVerification_date(now);
        r.setUser_id(u.getId());
        r.setIs_challenged(rider.isDisabled());
        r.setDeviceID(rider.getDeviceID());

        long riderid = riderRepository.addRider(r);
        rider.setRiderID(riderid);
        return rider;
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

    public void cancellRideRequest(CancellRideRequestObject request) throws URISyntaxException {

        com.woc.entity.Rider rider = riderRepository.findByID(request.getRiderID());
        RideRequest rideRequest = riderRequestsRepository.findByRider(rider);
        com.woc.entity.Driver driver = rideRequest.getDriverId();
        List<Long> driverIds = new ArrayList<Long>();
        driverIds.add(driver.getId());
        driverRepository.updateDriversStatus("Available", driverIds);

        List<String> driverAndroidId = new ArrayList<>();
        driverAndroidId.add(driver.getDeviceID());
        pushNotificationService.send(PushNotificationIdentifierEnum.RIDE_CANCELLED_BY_RIDER, null, driverAndroidId);
        riderRequestsRepository.deleteRideRequest(rideRequest);
        return;
    }

    public RideRequest getRideRequest(RideRequestUpdateObject requestObject) {
        return riderRequestsRepository.findById(requestObject.getRideRequestID());
    }

    public Rider updateRider(Rider rider) {

        if ((rider.getRiderID() == 0l) && (rider.getPhoneNumber() == null || rider.getPhoneNumber().trim().isEmpty())) {
            return null;
        }

        Rider r = new Rider();
        RiderSearchCriteria search = new RiderSearchCriteria();
        if (rider.getRiderID() != 0l) {
            search.setRiderID(rider.getRiderID());
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
            // r.setEmail(rider.getEmail());
            // r.setName(rider.getName());
        } else if (rider.getEmail() != null && !rider.getEmail().trim().isEmpty()) {
            System.out.println("Gonna update rider with email alone ");

            user_update = userRepository.updateUser("", rider.getEmail(), rider.getPhoneNumber(), r.getUserId());
            r.setEmail(rider.getEmail());

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
            // r.setName(rider.getName());
        }

        if (rider.getDocuments() != null || (rider.getDeviceID() != null && !rider.getDeviceID().trim().isEmpty())) {
            user_update = riderRepository.updateRiderData(rider);

        }

        if (user_update == 0) {

        }
        return riderRepository.getRider(search);
    }

    public Rider updateRiderPin(PINUpdateRequestObject request) {
        if ((request.getRiderID() == 0) || (request.getPIN() == null && request.getPIN().trim().isEmpty())) {
            return null;
        }
        return riderRepository.updateRiderPin(request);
    }

    public List<TripDto> getRiderTrips(TripSearchCriteria searchCriteria) {
        List<TripDto> fetchedTripDtos = new ArrayList<TripDto>();
        List<com.woc.entity.Trip> trips = tripRepository.getTrips(searchCriteria);
        if (trips == null) {
            return fetchedTripDtos;
        }
        for (com.woc.entity.Trip each : trips) {
            TripDto t = new TripDto();
            t.setDistance(each.getDistance());
            t.setDuration(each.getDuration());
            t.setStartTime(each.getTripStartTime());
            t.setEndTime(each.getTripEndTime());
            t.setFare(each.getCost());

            t.setStartLocation(each.getStartLocation());
            t.setEndLocation(each.getEndLocation());
            fetchedTripDtos.add(t);
        }
        return fetchedTripDtos;
    }

    RiderService() {
        super();
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

        boolean feedbackAlreadyExists = feedbackRepository.doesFeedbackAlreadyExist(trip.getId(), riderProfile.getId());
        if (feedbackAlreadyExists) {
            return 1;
        }

        long count = feedbackRepository.getFeedbackCountForUser(driverProfile.getId());
        Double updateRating = count == 0 ? feedbackDTO.getRating()
                : ((driverProfile.getRating() * count + feedbackDTO.getRating()) / (count + 1));
        userRepository.updateUserRating(updateRating, driverProfile.getId());

        feedback.setTripId(trip.getId());
        feedback.setUserId(driverProfile.getId());
        feedback.setFeedbackOwnerId(riderProfile.getId());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setComment(feedbackDTO.getComments());

        feedbackRepository.submitFeedback(feedback);
        return 1;
    }
}
