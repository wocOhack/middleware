package com.woc.controller;

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
import com.woc.dto.PINUpdateRequestObject;
import com.woc.dto.RideRequestObject;
import com.woc.dto.Rider;
import com.woc.dto.RiderSearchCriteria;
import com.woc.dto.Trip;
import com.woc.dto.TripSearchCriteria;
import com.woc.entity.ServiceableArea;
import com.woc.service.DriverService;
import com.woc.service.RiderService;

@RestController
@RequestMapping("/woc/rider")
public class RiderController {

    @Autowired
    RiderService riderService;

    @Autowired
    DriverService driverService;

    @PostMapping("/createProfile")
    public ResponseEntity createNewRider(@RequestBody Rider newRider) {
        riderService.addRider(newRider);
        long id = newRider.getRiderID();
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

    @GetMapping("getTrips")
    public List<Trip> getTrips(@RequestBody TripSearchCriteria criteria) {

        List<Trip> trips = new ArrayList<Trip>();
        return trips;

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
}
