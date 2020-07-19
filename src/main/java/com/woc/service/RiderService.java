package com.woc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woc.dto.CancellRideRequestObject;
import com.woc.dto.RideRequestObject;
import com.woc.dto.RideRequestUpdateObject;
import com.woc.dto.Rider;
import com.woc.dto.RiderSearchCriteria;
import com.woc.entity.RideRequest;
import com.woc.entity.ServiceableArea;
import com.woc.entity.User;
import com.woc.repository.DriverRepository;
import com.woc.repository.RideRequestRepository;
import com.woc.repository.RiderRepository;
import com.woc.repository.ServicableAreaRepository;
import com.woc.repository.UserRepository;

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

    public ServiceableArea addArea(ServiceableArea area) {
        return servicableAreaRepository.addArea(area);
    }

    public Iterable<ServiceableArea> getAllAreas() {
        return servicableAreaRepository.findAll();
    }

    public void addRider(Rider rider) {
        System.out.println("Adding rider");
        Date now = new Date(System.currentTimeMillis());

        com.woc.entity.Rider r = new com.woc.entity.Rider();
        User u = new User();
        
        RiderSearchCriteria search =  new RiderSearchCriteria();
        search.setPhoneNumber(rider.getPhoneNumber());
        Rider ifexisting = riderRepository.getRider(search);
        if (ifexisting == null) {
            // send 400 bad request as user already exist.... 
            System.out.println("User already exist.....");
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
        // r.setProof_of_challenge(rider.get);
        r.setVerification_date(now);
        r.setUser_id(u.getId());
        r.setIs_challenged(true);
        riderRepository.addRider(r);
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
    
    RiderService() {
        super();
    }

}
