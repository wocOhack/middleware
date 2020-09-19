package com.woc.controller;

import com.woc.service.OTPService;
import com.woc.dto.*;
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
import com.woc.dto.RideRequestUpdateObject;
import com.woc.dto.WocResponseBody;
import com.woc.entity.RideRequest;
import com.woc.service.DriverService;
import com.woc.service.RiderService;
import com.woc.dto.PhoneVerificationInitiationRequest;
import com.woc.dto.DriverVerificationCompletionReply;
import com.woc.dto.PhoneVerificationCompletionRequest;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/woc/driver")
public class DriverController {

    private static final String INTERNAL_ERROR = "INTERNAL_ERROR";
    private static final String INTERNAL_ERROR_MESSAGE = "Internal server error";
    private static final String BAD_REQUEST = "BAD_REQUEST";
    private static final String BAD_REQUEST_MESSAGE = "Client error";

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
            Driver d = request.getDriver();
            d.setDriverID(id);
            request.setDriver(d);
            resp.setResult(request);
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
        Driver d = driverService.updateDriver(request.getDriver(), request.getVehicle(), request.getInsurance());
        WocResponseBody resp = new WocResponseBody();
        if (d == null) {
            resp.setDetailedMessage("DriverId or phoneNumber is required to identify driver");
            resp.setResponseStatus("Bad Request");
            return new ResponseEntity(resp, HttpStatus.BAD_REQUEST);
        }
        if (d.getDriverID() == 0) {
            resp.setDetailedMessage("Internal Server Error");
            resp.setResponseStatus("Issue updating Driver");
            return new ResponseEntity(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resp.setResult(d);
        resp.setDetailedMessage("OK");
        resp.setResponseStatus("Successfully Updated Driver");
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @PostMapping("/getProfile")
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

    @PostMapping("/acceptRideRequest")
    public ResponseEntity acceptRideRequest(@RequestBody AcceptRideRequestDTO acceptRideRequestDTO) {
        WocResponseBody wocResponseBody = null;
        RideRequest request = riderService.getRideRequest(acceptRideRequestDTO.getRideId());
        if (null == request || null != request.getDriverId()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        try {
			driverService.acceptRideRequest(acceptRideRequestDTO.getDriverId(), request);
		} catch (Exception e) {
            wocResponseBody = new WocResponseBody();
            wocResponseBody.setResponseStatus(INTERNAL_ERROR);
            wocResponseBody.setDetailedMessage(INTERNAL_ERROR_MESSAGE);
            return new ResponseEntity(wocResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        StringBuffer responseBody = new StringBuffer("");
        responseBody.append("rideId:").append(acceptRideRequestDTO.getRideId()).append(",");
        responseBody.append("source:").append(request.getStartLocation()).append(",");
        responseBody.append("destination:").append(request.getEndLocation()).append("}");

      return new ResponseEntity(wocResponseBody, HttpStatus.OK);
    }
    
    @PostMapping("/rejectRideRequest")
    public ResponseEntity rejectRideRequest(@RequestBody RejectRideRequestDTO rejectRideRequestDTO) {

    	return new ResponseEntity(" ", HttpStatus.OK);
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
    public WocResponseBody completePhoneVerification(@RequestBody final PhoneVerificationCompletionRequest phoneVerificationCompletionRequest) {
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
        
        DriverVerificationCompletionReply driverVerificationCompletionReply= new DriverVerificationCompletionReply(isVerified, isExistingUser, driver);
        
        if(!driverVerificationCompletionReply.getIsVerified()){
            return new WocResponseBody(driverVerificationCompletionReply,"OTP_VERIFICATION_FAILURE","OTP did not match or verification is yet to be initiated");
        }
        else{
            return new WocResponseBody(driverVerificationCompletionReply,"OTP_VERIFICATION_SUCCESS","OTP VERIFIED SUCCESSFULLY");
        }
        
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
        if (updateLocationRequest.getDriverId() == 0l && (updateLocationRequest.getLocation() == null
                || updateLocationRequest.getLocation().trim().isEmpty())) {
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
    public ResponseEntity submitFeedBack(@RequestBody FeedbackDto feedbackDto) {
        WocResponseBody wocResponseBody = null;
        try {
            int status = driverService.submitFeedback(feedbackDto);
            if (status < 1) {
                if (status == -1) {
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

    @PostMapping("/startRide")
    public ResponseEntity startRide(@RequestBody StartRideRequestDto startRideRequestDto) {
        WocResponseBody wocResponseBody = null;
        StartRideResponseDto startRideResponseDto;
        try {
            Long tripId = driverService.startRide(startRideRequestDto);
            if (tripId < 1) {
                if (tripId == -1l) {
                    startRideResponseDto = new StartRideResponseDto(null);
                    wocResponseBody = new WocResponseBody(startRideResponseDto, BAD_REQUEST, BAD_REQUEST_MESSAGE);
                } else if (tripId == -2l) {
                    startRideResponseDto = new StartRideResponseDto(null);
                    wocResponseBody = new WocResponseBody(startRideResponseDto, "INVALID_PIN",
                            "PIN entered by the driver does not match");
                }
                return new ResponseEntity(wocResponseBody, HttpStatus.BAD_REQUEST);
            } else {
                startRideResponseDto = new StartRideResponseDto(tripId);
                wocResponseBody = new WocResponseBody(startRideResponseDto, "TRIP_IN_PROGRESS",
                        "The trip has started successfully");
            }
            return new ResponseEntity(wocResponseBody, HttpStatus.CREATED);
        } catch (Exception e) {
            wocResponseBody = new WocResponseBody();
            wocResponseBody.setResponseStatus(INTERNAL_ERROR);
            wocResponseBody.setDetailedMessage(INTERNAL_ERROR_MESSAGE);
            return new ResponseEntity(wocResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/endRide")
    public ResponseEntity endRide(@RequestBody EndRideRequestDto endRideRequestDto) {
        WocResponseBody wocResponseBody = null;
        EndRideResponseDto endRideResponseDto;
        try {
            endRideResponseDto = driverService.endRide(endRideRequestDto);
            if (endRideResponseDto == null) {
                wocResponseBody = new WocResponseBody(endRideResponseDto, BAD_REQUEST, BAD_REQUEST_MESSAGE);
                return new ResponseEntity(wocResponseBody, HttpStatus.BAD_REQUEST);
            }
            wocResponseBody = new WocResponseBody(endRideResponseDto, "TRIP_ENDED", "The trip has ended successfully.");
            return new ResponseEntity(wocResponseBody, HttpStatus.OK);
        } catch (Exception e) {
            wocResponseBody = new WocResponseBody(null, INTERNAL_ERROR, INTERNAL_ERROR_MESSAGE);
            return new ResponseEntity(wocResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
