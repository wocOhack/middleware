package com.woc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woc.dto.PINUpdateRequestObject;
import com.woc.dto.RideRequestObject;
import com.woc.dto.Rider;
import com.woc.dto.RiderSearchCriteria;
import com.woc.dto.Trip;
import com.woc.dto.TripSearchCriteria;
import com.woc.entity.ServiceableArea;
import com.woc.service.RiderService;

@RestController
@RequestMapping("/woc/rider")
public class RiderController {

	@Autowired
	RiderService riderService;
	
	@PostMapping("/createProfile")
	public long createNewRider(@RequestBody Rider newRider) {
		return 1L;
	}
	
	@PutMapping("/updateProfile")
	public void updateRiderProfile(@RequestBody Rider rider) {
		return;
	}
	
	@GetMapping("/getProfile")
	public Rider getRiderProfile(@RequestBody RiderSearchCriteria searchCriteria) {
		Rider rider = new Rider();
		rider.setName("Harry Potter");
		rider.setPhoneNumber("123456789");
		return rider;
	}
	
	@PutMapping("/updatePIN")
	public void updatePIN(@RequestBody PINUpdateRequestObject pinUpdateRequestObject) {
		return;
	}
	
	@PostMapping("/requestRide")
	public void requestRide(@RequestBody RideRequestObject rideRequest) {
		return;
	}
	
	@PostMapping("/cancelRide")
	public void cancelRide(@RequestBody Long riderID) {
		return;
	}
	
	@GetMapping("getTrips")
	public List<Trip> getTrips(@RequestBody TripSearchCriteria criteria){
		
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
