package com.woc.controller;

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
import com.woc.dto.DriverRegistrationRequest;
import com.woc.dto.DriverSearchCriteria;
import com.woc.dto.FeedBack;
import com.woc.dto.RideRequestUpdateObject;
import com.woc.dto.StartRideRequestObject;
import com.woc.dto.Trip;
import com.woc.service.DriverService;

@RestController
@RequestMapping("/woc/driver")
public class DriverController {

    @Autowired
    DriverService driverService;

    @PostMapping("/createProfile")
    public ResponseEntity createNewDriver(@RequestBody DriverRegistrationRequest request) {
        long id = driverService.addDriver(request.getDriver(), request.getVehicle(), request.getInsurance());
        // return 2L;
        if (id != 0) {
            String message = "Driver created sucessfully";
            return new ResponseEntity(message, HttpStatus.CREATED);
        } else if (id == -1) {
            String message = "Driver already exist with following phone number";
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        } else {
            String message = "Issue creating driver";
            return new ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateProfile")
    public void updateDriverProfile(@RequestBody DriverRegistrationRequest request) {
        return;
    }

    @GetMapping("/getProfile")
    public ResponseEntity getDriverProfile(@RequestBody DriverSearchCriteria searchCriteria) {
        Driver driver = driverService.getDriver(searchCriteria);
        // driver.setName("Ron Weisly");
        // driver.setPhoneNumber("9876543210");
        if (driver == null) {
            String message = "Data Not Found";
            return new ResponseEntity(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(driver, HttpStatus.OK);

    }

    @PostMapping("/updateAvailability")
    public ResponseEntity updateDriverAvailability(@RequestBody DriverAvailability driverAvailability) {
        // return;
        long result = driverService.toggleDriverAvailability(driverAvailability);
        if (result == 0l) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateRideRequest")
    public void updateRideRequest(@RequestBody RideRequestUpdateObject rideRequestUpdateObject) {
        return;
    }

    @PostMapping("/startRide")
    public void startRide(@RequestBody StartRideRequestObject startRideRequestObject) {
        return;
    }

    @PostMapping("/endRide")
    public Trip endRide(@RequestBody StartRideRequestObject startRideRequestObject) {
        Trip trip = new Trip();
        trip.setFare(200L);
        return trip;
    }

    @PostMapping("/submitFeedBack")
    public void submitFeedBack(@RequestBody FeedBack feedBack) {
        return;
    }

    @PutMapping("/toggleDriverAvailabilityStatus")
    public void toggleDriverAvailability(@RequestBody DriverAvailability driverAvailability) {
        boolean status = driverAvailability.getStatus();
        long user_id = driverAvailability.getDriverID();
        driverService.toggleDriverAvailability(user_id, status);
    }
}
