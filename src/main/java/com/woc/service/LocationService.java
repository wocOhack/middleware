package com.woc.service;

import org.springframework.stereotype.Component;
import com.woc.util.LocationUtility;

@Component
public class LocationService {

	public double getDistanceBetweenLocations(String location1, String location2) {
		return LocationUtility.distance(location1.split(":"), location2.split(":"));
	}
}
