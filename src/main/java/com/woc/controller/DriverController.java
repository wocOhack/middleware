package com.woc.controller;

import com.woc.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woc.dto.Driver;
import com.woc.dto.DriverAvailability;
import com.woc.dto.DriverLocationUpdateRequest;
import com.woc.dto.DriverRegistrationRequest;
import com.woc.dto.DriverSearchCriteria;
import com.woc.dto.FeedBack;
import com.woc.dto.RideRequestUpdateObject;
import com.woc.dto.StartRideRequestObject;
import com.woc.dto.Trip;
import com.woc.dto.WocResponseBody;
import com.woc.entity.RideRequest;
import com.woc.service.DriverService;
import com.woc.service.RiderService;
import com.woc.service.exceptions.FeedbackSubmissionException;
import com.woc.dto.PhoneVerificationInitiationRequest;
import com.woc.dto.DriverVerificationCompletionReply;
import com.woc.dto.PhoneVerificationCompletionRequest;

@RestController
@RequestMapping("/woc/driver")
public class DriverController {

    @Autowired
    DriverService driverService;

    @Autowired
    OTPService otpService;

    @Autowired
    RiderService riderService;

    @PostMapping("/createProfile")
    public ResponseEntity createNewDriver(@RequestBody DriverRegistrationRequest request) {
        long id = driverService.addDriver(request.getDriver(), request.getVehicle(), request.getInsurance());
        // return 2L;
        WocResponseBody resp = new WocResponseBody();
        if (id != 0 && id != -1) {
            resp.setDetailedMessage("OK");
            resp.setResponseStatus("Driver created sucessfully");
            return new ResponseEntity(resp, HttpStatus.CREATED);
        } else if (id == -1) {
            resp.setDetailedMessage("Bad Request");
            resp.setResponseStatus("Driver already exist with following phone number");
            
            return new ResponseEntity(resp, HttpStatus.BAD_REQUEST);
        } else {
            resp.setDetailedMessage("Internal Server Error");
            resp.setResponseStatus("Issue creating driver");

            return new ResponseEntity(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateProfile")
    public ResponseEntity updateDriverProfile(@RequestBody DriverRegistrationRequest request) {
        long id = driverService.updateDriver(request.getDriver(), request.getVehicle(), request.getInsurance());
        WocResponseBody resp = new WocResponseBody();
        if (id == 0) {
            resp.setDetailedMessage("Internal Server Error");
            resp.setResponseStatus("Issue updating Driver");
            
            return new ResponseEntity(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resp.setDetailedMessage("OK");
        resp.setResponseStatus("Successfully Updated Driver");
        return new ResponseEntity("Successfully Updated Driver", HttpStatus.OK);
    }

    @GetMapping("/getProfile")
    public ResponseEntity getDriverProfile(@RequestBody DriverSearchCriteria searchCriteria) {
        Driver driver = driverService.getDriver(searchCriteria);
        WocResponseBody resp = new WocResponseBody();
        // driver.setName("Ron Weisly");
        // driver.setPhoneNumber("9876543210");
        if (driver == null) {
            resp.setDetailedMessage("Data Not Found");
            resp.setResponseStatus("Data Not available for requested driver");
            
            return new ResponseEntity(resp, HttpStatus.NOT_FOUND);
        }
        resp.setDetailedMessage("OK");
        resp.setResponseStatus("Successfully Retrieved data");
        resp.setResult(driver);
        return new ResponseEntity(resp, HttpStatus.OK);

    }

    @PostMapping("/updateAvailability")
    public ResponseEntity updateDriverAvailability(@RequestBody DriverAvailability driverAvailability) {
        // return;
        long result = driverService.toggleDriverAvailability(driverAvailability);
        WocResponseBody resp = new WocResponseBody();
        if (result != 0l) {
            resp.setDetailedMessage("OK");
            resp.setResponseStatus("Successfully Updated Driver Availability");
            return new ResponseEntity(resp, HttpStatus.OK);
        } else {
            resp.setDetailedMessage("Internal Server Error");
            resp.setResponseStatus("Issue Updating Driver Availability");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateRideRequest")
    public ResponseEntity updateRideRequest(@RequestBody RideRequestUpdateObject rideRequestUpdateObject) {

        RideRequest request = riderService.getRideRequest(rideRequestUpdateObject);
        if (null == request || null != request.getDriverId()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        driverService.acceptRideRequest(rideRequestUpdateObject, request);
        // if found and no driver alloted, then allot the driver, send push notification to rider
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/startRide")
    public void startRide(@RequestBody StartRideRequestObject startRideRequestObject) {
        return;
    }

    @PostMapping("/endRide")
    public Trip endRide(@RequestBody StartRideRequestObject startRideRequestObject) {
        Trip trip = new Trip();
        trip.setFare(200.0);
        return trip;
    }

    @PostMapping("/initiatePhoneVerification")
    public Boolean initiatePhoneVerification(@RequestBody final PhoneVerificationInitiationRequest phoneVerificationInitiationRequest) {
        return otpService.initiateVerification(phoneVerificationInitiationRequest);
    }

    @PutMapping("/completePhoneVerification")
    public DriverVerificationCompletionReply completePhoneVerification(@RequestBody final PhoneVerificationCompletionRequest phoneVerificationCompletionRequest) {
        Boolean isExistingUser = false;
        Driver driver = null;
        Boolean isVerified = otpService.completeVerification(phoneVerificationCompletionRequest);

        if (isVerified) {
            String phoneNumber = phoneVerificationCompletionRequest.getPhoneNumber();
            DriverSearchCriteria driverSearchCriteria = new DriverSearchCriteria();
            driverSearchCriteria.setPhoneNumber(phoneNumber);
            driver = driverService.getDriver(driverSearchCriteria);
            isExistingUser = (driver != null);
        }
        return new DriverVerificationCompletionReply(isVerified, isExistingUser, driver);
    }
    @PutMapping("/toggleDriverAvailabilityStatus")
    public void toggleDriverAvailability(@RequestBody DriverAvailability driverAvailability) {
        String status = driverAvailability.getStatus();
        long user_id = driverAvailability.getDriverID();
        driverService.toggleDriverAvailability(user_id, status);
    }

    @PutMapping("/update-current-location")
    public ResponseEntity updateCurrentLocation(@RequestBody DriverLocationUpdateRequest updateLocationRequest) {
        System.out.println(updateLocationRequest.getDriverId() + " " + updateLocationRequest.getLocation());
        WocResponseBody resp = new WocResponseBody();
        if (updateLocationRequest.getDriverId() == 0l && (updateLocationRequest.getLocation()== null || updateLocationRequest.getLocation().trim().isEmpty())) {
            resp.setDetailedMessage("Bad Request");
            resp.setResponseStatus("Need driverid and location for the update request");
            
            return new ResponseEntity(resp, HttpStatus.BAD_REQUEST); 
        } 
        long updated = driverService.updateDriverLocation(updateLocationRequest);
        if (updated != 0l) {
            resp.setDetailedMessage("OK");
            resp.setResponseStatus("Driver Location Updated");
            
            return new ResponseEntity(resp, HttpStatus.OK); 
        }
        resp.setDetailedMessage("Internal Server Error");
        resp.setResponseStatus("Issue Updating Driver Location");
        
        return new ResponseEntity(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

	@PostMapping("/submitFeedBack")
	public ResponseEntity submitFeedBack(@RequestBody FeedBack feedBack) {
		try {
			driverService.submitFeedback(feedBack);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
		    if(e instanceof FeedbackSubmissionException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
