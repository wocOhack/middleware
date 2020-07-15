package com.woc.repository;

import com.woc.dto.TripSearchCriteria;
import com.woc.entity.Trip;

import java.util.List;

public interface TripRepository {

    public List<Trip> findAll();
        
    public Trip findTripById(long id);
    
    //public Trip findTripsByRiderId(long id);
    
    //public Trip findTripsByDriverId(long id);

    public Trip createTrip(Trip trip);
    
    public Trip updateTrip(Trip trip);
    
    public List<Trip> getTrips(TripSearchCriteria searchCriteria);

}
