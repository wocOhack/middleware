package com.woc.controller;

import java.util.ArrayList;
import java.util.List;

import com.woc.dto.*;
import com.woc.service.exceptions.FeedbackSubmissionException;
import com.woc.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woc.entity.ServiceableArea;
import com.woc.service.DriverService;
import com.woc.service.RiderService;
import com.woc.dto.PhoneVerificationInitiationRequest;
import com.woc.dto.RiderVerificationCompletionReply;
import com.woc.dto.PhoneVerificationCompletionRequest;
@RestController
@RequestMapping("/woc/rider")
public class RiderController {

    @Autowired
    RiderService riderService;

    @Autowired
    DriverService driverService;

    @Autowired
    OTPService otpService;

    @PostMapping("/createProfile")
    public ResponseEntity createNewRider(@RequestBody Rider newRider) {

        long id = riderService.addRider(newRider);
        if (id != 0) {
            String message = "Rider created sucessfully";
            return new ResponseEntity(message, HttpStatus.CREATED);
        } else if (id == -1) {
            String message = "Rider already exist with following phone number";
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else {
            String message = "Issue creating rider";
            return new ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateProfile")
    public ResponseEntity updateRiderProfile(@RequestBody Rider rider) {
        long id = riderService.updateRider(rider);
        if (id != 0) {
            return new ResponseEntity("", HttpStatus.OK);
        }
        return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getProfile")
    public ResponseEntity getRiderProfile(@RequestBody RiderSearchCriteria searchCriteria) {
        // Rider rider = new Rider();
        // rider.setName("Harry Potter");
        // rider.setPhoneNumber("123456789");
        Rider rider = riderService.getRider(searchCriteria);
        if (rider == null) {
            String message = "Data Not Found";
            return new ResponseEntity(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(rider, HttpStatus.OK);
    }

    @PutMapping("/updatePIN")
    public ResponseEntity updatePIN(@RequestBody PINUpdateRequestObject pinUpdateRequestObject) {
        System.out.println(pinUpdateRequestObject.getPIN() + pinUpdateRequestObject.getRiderID());
        long id = riderService.updateDriverPin(pinUpdateRequestObject);
        if (id == -1) {
            return new ResponseEntity("Both riderId and pin required for pin Update", HttpStatus.BAD_REQUEST);
        }
        if (id == 0) {
            return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("", HttpStatus.OK);
    }

    @PostMapping("/requestRide")
    public void requestRide(@RequestBody RideRequestObject rideRequest) {

        long rideRequestID = riderService.createRideRequest(rideRequest);
        driverService.notifyNearestDrivers(rideRequest.getSourceLocation(), rideRequest.getDestinationLocation(),
                rideRequestID);
        return;
    }

    @PostMapping("/cancelRide")
    public void cancelRide(@RequestBody CancellRideRequestObject request) {
        riderService.cancellRideRequest(request);
        ;
    }

    @GetMapping("/getTrips")
    public ResponseEntity getTrips(@RequestBody TripSearchCriteria criteria) {
        List<Trip> trips = riderService.getRiderTrips(criteria);
        if (trips.size() == 0) {
            return new ResponseEntity("Data Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(trips, HttpStatus.OK);

    }

    @PostMapping("/addServicableArea")
    public ServiceableArea addServicableArea(@RequestBody ServiceableArea a) {
        ServiceableArea area = riderService.addArea(a);
        return area;
    }

    @GetMapping("getServicableAreas")
    public List<ServiceableArea> getAllServicableAreas() {

        List<ServiceableArea> areas = new ArrayList<ServiceableArea>();
        areas = (List<ServiceableArea>) riderService.getAllAreas();
        return areas;

    }

    @PostMapping("/submitFeedBack")
    public ResponseEntity submitFeedBack(@RequestBody FeedBack feedBack) {
        try {
            riderService.submitFeedback(feedBack);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            if(e instanceof FeedbackSubmissionException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/initiatePhoneVerification")
    public Boolean initiatePhoneVerification(@RequestBody PhoneVerificationInitiationRequest phoneVerificationInitiationRequest) {
        return otpService.initiateVerification(phoneVerificationInitiationRequest);
    }

    @PutMapping("/completePhoneVerification")
    public RiderVerificationCompletionReply completePhoneVerification(@RequestBody PhoneVerificationCompletionRequest phoneVerificationCompletionRequest) {
        Boolean isExistingUser=false;
        Rider rider=null;
        Boolean isVerified=otpService.completeVerification(phoneVerificationCompletionRequest);

        if(isVerified){
            String phoneNumber=phoneVerificationCompletionRequest.getPhoneNumber();
            RiderSearchCriteria riderSearchCriteria=new RiderSearchCriteria();
            riderSearchCriteria.setPhoneNumber(phoneNumber);
            rider=riderService.getRider(riderSearchCriteria);
            isExistingUser=(rider!=null);
        }
        return new RiderVerificationCompletionReply(isVerified,isExistingUser,rider);
    }

}
