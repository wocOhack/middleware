package com.woc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DriverMatchUtilityService {

	public List<String> findNearestDrivers(String location) {
		
		List<String> nearestDrivers = new ArrayList<String>();
		//make db call to get all available drivers
		//call google location api to find distance of all drivers
		//arrange drivers in ascending order of distance
		//send push notification to top 5 drivers
		return nearestDrivers;
	}
}
