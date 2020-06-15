package com.woc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woc.dto.Driver;
import com.woc.dto.DriverSearchCriteria;
import com.woc.service.DriverService;

@RestController
@RequestMapping("/woc/driver")
public class DriverController {

	//@Autowired
	DriverService driverService;
	
	@PostMapping("/createProfile")
	public long createNewDriver(@RequestBody Driver newDriver) {
		return 2L;
	}
	
	@PutMapping("/updateProfile")
	public void updateDriverProfile(@RequestBody Driver driver) {
		return;
	}
	
	
	@GetMapping("/getProfile")
	public Driver getDriverProfile(@RequestBody DriverSearchCriteria searchCriteria) {
		Driver driver = new Driver();
		driver.setName("Ron Weisly");
		driver.setPhoneNumber("9876543210");
		return driver;
	}
}
