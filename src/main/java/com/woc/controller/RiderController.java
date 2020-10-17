package com.woc.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woc.dto.CancellRideRequestObject;
import com.woc.dto.FeedbackDto;
import com.woc.dto.PINUpdateRequestObject;
import com.woc.dto.PhoneVerificationCompletionRequest;
import com.woc.dto.PhoneVerificationInitiationRequest;
import com.woc.dto.RideRequestObject;
import com.woc.dto.Rider;
import com.woc.dto.RiderLocaionUpdateRequest;
import com.woc.dto.RiderSearchCriteria;
import com.woc.dto.RiderVerificationCompletionReply;
import com.woc.dto.TripDto;
import com.woc.dto.TripSearchCriteria;
import com.woc.dto.WocResponseBody;
import com.woc.entity.ServiceableArea;
import com.woc.service.DriverService;
import com.woc.service.OTPService;
import com.woc.service.RiderService;
import com.woc.dto.PhoneVerificationInitiationRequest;
import com.woc.dto.RiderVerificationCompletionReply;
import com.woc.dto.PhoneVerificationCompletionRequest;

@RestController
@RequestMapping("/woc/rider")
public class RiderController {

    private static final String INTERNAL_ERROR = "INTERNAL_ERROR";
    private static final String INTERNAL_ERROR_MESSAGE = "Internal server error";
    private static final String BAD_REQUEST = "BAD_REQUEST";
    private static final String BAD_REQUEST_MESSAGE = "Client error";

    @Autowired
    RiderService riderService;

    @Autowired
    DriverService driverService;

    @Autowired
    OTPService otpService;

    @PostMapping("/createProfile")
    public ResponseEntity createNewRider(@RequestBody Rider newRider) {
        
        Rider rider = riderService.addRider(newRider);
        WocResponseBody resp = new WocResponseBody();
        if (rider.getRiderID() != 0 && rider.getRiderID() != -1) {
            String message = "OK";
            resp.setResult(rider);
            resp.setResponseStatus(message);
            resp.setDetailedMessage("Rider Created Successfully");
            return new ResponseEntity(resp, HttpStatus.CREATED);
        } else if (rider.getRiderID() == -1) {
            String message = "Bad Request";
            resp.setResponseStatus(message);
            resp.setDetailedMessage("Rider already exist with following phone number");
            return new ResponseEntity(resp, HttpStatus.BAD_REQUEST);
        } else {
            String message = "Internal Server Error";
            resp.setResponseStatus(message);
            resp.setDetailedMessage("Issue creating rider");
            return new ResponseEntity(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateProfile")
    public ResponseEntity updateRiderProfile(@RequestBody Rider rider) {
        WocResponseBody resp = new WocResponseBody();
        Rider r = riderService.updateRider(rider);
        if (r != null) {
            resp.setResult(r);
            resp.setResponseStatus("OK");
            resp.setDetailedMessage("Rider Updated Successfully");
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        resp.setDetailedMessage("Issue Updating Rider");
        resp.setResponseStatus("Internal Server Error");
        return new ResponseEntity(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/getProfile")
    public ResponseEntity getRiderProfile(@RequestBody RiderSearchCriteria searchCriteria) {
        // Rider rider = new Rider();
        // rider.setName("Harry Potter");
        // rider.setPhoneNumber("123456789");
        Rider rider = riderService.getRider(searchCriteria);
        WocResponseBody resp = new WocResponseBody();
        if (rider == null) {
            resp.setResponseStatus("Data Not Found");
            resp.setDetailedMessage("No data Available for the given Rider");
            return new ResponseEntity(resp, HttpStatus.NOT_FOUND);
        }
        resp.setResponseStatus("OK");
        resp.setDetailedMessage("Successfully Retrieved data");
        resp.setResult(rider);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @PutMapping("/update-current-location")
    public ResponseEntity updateCurrentLocation(@RequestBody RiderLocaionUpdateRequest updateLocationRequest) {
        System.out.println(updateLocationRequest.getRiderId() + " " + updateLocationRequest.getLocation());
        WocResponseBody resp = new WocResponseBody();
        if (updateLocationRequest.getRiderId() == 0l && (updateLocationRequest.getLocation() == null
                || updateLocationRequest.getLocation().trim().isEmpty())) {
            resp.setDetailedMessage("Bad Request");
            resp.setResponseStatus("Need riderId and location for the update request");

            return new ResponseEntity(resp, HttpStatus.BAD_REQUEST);
        }
        long updated = riderService.updateRiderLocation(updateLocationRequest);
        if (updated != 0l) {
            resp.setDetailedMessage("OK");
            resp.setResponseStatus("Driver Location Updated");

            return new ResponseEntity(resp, HttpStatus.OK);
        }
        resp.setDetailedMessage("Internal Server Error");
        resp.setResponseStatus("Issue Updating Rider Location");

        return new ResponseEntity(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/updatePIN")
    public ResponseEntity updatePIN(@RequestBody PINUpdateRequestObject pinUpdateRequestObject) {
        System.out.println(pinUpdateRequestObject.getPIN() + pinUpdateRequestObject.getRiderID());
        Rider rider = riderService.updateRiderPin(pinUpdateRequestObject);
        WocResponseBody resp = new WocResponseBody();
        if (rider == null) {
            resp.setResponseStatus("Bad Request");
            resp.setDetailedMessage("Both riderId and pin required for pin Update");
            return new ResponseEntity(resp, HttpStatus.BAD_REQUEST);
        }
        if (rider.getRiderID() == 0) {
            resp.setResponseStatus("Internal Server Error");
            resp.setDetailedMessage("Issue updating Pin");
            return new ResponseEntity(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resp.setResponseStatus("OK");
        resp.setDetailedMessage("Pin Successfully Updated");
        resp.setResult(rider);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @PostMapping("/requestRide")
    public ResponseEntity requestRide(@RequestBody RideRequestObject rideRequest) {
    	
        WocResponseBody wocResponseBody = null;
        long rideRequestID = riderService.createRideRequest(rideRequest);
        String sourceLocation = rideRequest.getSourceLattitude() + ":" + rideRequest.getSourceLongitude();
        try {
			driverService.notifyNearestDrivers(sourceLocation, rideRequestID);
			wocResponseBody = new WocResponseBody();
			wocResponseBody.setResponseStatus("OK");
			wocResponseBody.setDetailedMessage("rideRequestId:"+rideRequestID);
		} 
		catch (Exception e) {
	            wocResponseBody = new WocResponseBody();
	            wocResponseBody.setResponseStatus(INTERNAL_ERROR);
	            wocResponseBody.setDetailedMessage(INTERNAL_ERROR_MESSAGE);
	            return new ResponseEntity(wocResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        return new ResponseEntity(wocResponseBody, HttpStatus.CREATED);
    }

    @PostMapping("/cancelRide")
    public void cancelRide(@RequestBody CancellRideRequestObject request) throws URISyntaxException {
        riderService.cancellRideRequest(request);
    }

    @PostMapping("/getTrips")
    public ResponseEntity getTrips(@RequestBody TripSearchCriteria criteria) {
        List<TripDto> tripDtos = riderService.getRiderTrips(criteria);
        WocResponseBody resp = new WocResponseBody();

        if (tripDtos.size() == 0) {
            resp.setDetailedMessage("No data avilable for the request");
            resp.setResponseStatus("Data Not Found");
            return new ResponseEntity(resp, HttpStatus.NOT_FOUND);
        }
        resp.setDetailedMessage("OK");
        resp.setResponseStatus("Successfully Retrieved data");
        resp.setResult(tripDtos);
        return new ResponseEntity(resp, HttpStatus.OK);

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
    public ResponseEntity submitFeedBack(@RequestBody FeedbackDto feedbackDto) {
        WocResponseBody wocResponseBody = null;
        try {
            int status = riderService.submitFeedback(feedbackDto);
            if(status < 1) {
                if(status == -1) {
                    wocResponseBody = new WocResponseBody();
                    wocResponseBody.setResponseStatus(BAD_REQUEST);
                    wocResponseBody.setDetailedMessage(BAD_REQUEST_MESSAGE);
                } else if (status == -2) {
                    wocResponseBody = new WocResponseBody();
                    wocResponseBody.setResponseStatus("TRIP_STATUS_CONTINGENCY");
                    wocResponseBody.setDetailedMessage("Trip is in cancelled state or still in progress");
                }
                return new ResponseEntity(wocResponseBody, HttpStatus.BAD_REQUEST);
            } else {
                wocResponseBody = new WocResponseBody();
                wocResponseBody.setResponseStatus("FEEDBACK_RECEIVED");
                wocResponseBody.setDetailedMessage("Feedback submitted successfully");
            }
            return new ResponseEntity(wocResponseBody, HttpStatus.CREATED);
        } catch (Exception e) {
            wocResponseBody = new WocResponseBody();
            wocResponseBody.setResponseStatus(INTERNAL_ERROR);
            wocResponseBody.setDetailedMessage(INTERNAL_ERROR_MESSAGE);
            return new ResponseEntity(wocResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/initiatePhoneVerification")
    public WocResponseBody initiatePhoneVerification(@RequestBody final PhoneVerificationInitiationRequest phoneVerificationInitiationRequest) {
        Boolean result=otpService.initiateVerification(phoneVerificationInitiationRequest);
        if(result){
            return new WocResponseBody(result,"OK","OTP generated and sent");
        }
        return new WocResponseBody(result,"OTP_SENDING_ERROR","OTP generated but not sent");

    }

    @PutMapping("/completePhoneVerification")
    public WocResponseBody completePhoneVerification(@RequestBody PhoneVerificationCompletionRequest phoneVerificationCompletionRequest) {
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
        RiderVerificationCompletionReply riderVerificationCompletionReply=new RiderVerificationCompletionReply(isVerified,isExistingUser,rider);


        if(!riderVerificationCompletionReply.getIsVerified()){
            return new WocResponseBody(riderVerificationCompletionReply,"OTP_VERIFICATION_FAILURE","OTP did not match or verification is yet to be initiated");
        }
        else{
            return new WocResponseBody(riderVerificationCompletionReply,"OTP_VERIFICATION_SUCCESS","OTP VERIFIED SUCCESSFULLY");
        }
    }

}