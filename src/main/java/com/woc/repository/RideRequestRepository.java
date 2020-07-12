package com.woc.repository;

import java.util.List;

import com.woc.entity.RideRequest;
import com.woc.entity.Rider;
import com.woc.entity.User;

public interface RideRequestRepository {

	public List<RideRequest> findAll();

	public void addRideRequest(RideRequest r);

	public RideRequest findById(long id);

	public void deleteRideRequest(RideRequest rideRequest);

	public RideRequest findByRider(Rider rider);

	public void updateRideRequest(RideRequest rideRequest);

}
