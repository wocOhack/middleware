package com.woc.repository;

import com.woc.dto.TripSearchCriteria;
import com.woc.entity.Trip;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class TripRepositoryImpl implements TripRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Trip> findAll() {
        List<Trip> trips = entityManager.createNamedQuery("Trip.findAll").getResultList();
        return trips;
    }

    @Override
    public Trip findTripById(long id) {
        Query query = entityManager.createNamedQuery("Trip.findById");
        query.setParameter(1, id);
        List<Trip> results = query.getResultList();
        if (null == results || results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Trip createTrip(Trip trip) {
        entityManager.persist(trip);
        return trip;
    }

    @Override
    public Trip updateTrip(Trip trip) {

        return null;
    }

    @Override
    public List<Trip> getTrips(TripSearchCriteria searchCriteria) {

        if (searchCriteria.getRiderID() != 0) {
            long id = searchCriteria.getRiderID();
            List<Trip> trips = entityManager
                    .createNativeQuery("select * from Trip t where t.rider_id = " + id, Trip.class).getResultList();
            if (trips.size() == 0) {
                return null;
            }
            return trips;
        }
        if (searchCriteria.getDriverID() != 0) {
            long id = searchCriteria.getDriverID();
            List<Trip> trips = entityManager
                    .createNativeQuery("select * from Trip t where t.driver_id = " + id, Trip.class).getResultList();
            if (trips.size() == 0) {
                return null;
            }
            return trips;
        }
        return null;
    }

    public Long isTripInProgress(Long driverId, Long riderId) {
        List<Trip> tripsInProgress = entityManager
                .createNativeQuery("select * from Trip t where t.rider_id=" + riderId + " and t.driver_id=" + driverId + " and t.status='P'", Trip.class)
                .getResultList();
        if(tripsInProgress.size() == 0) {
            return 0l;
        }
        Collections.sort(tripsInProgress, new Comparator<Trip>() {

            @Override
            public int compare(Trip t1, Trip t2) {
                return (int) (t2.getUpdatedTime().getTime() - t1.getUpdatedTime().getTime());
            }
        });
        return tripsInProgress.get(0).getId();
    }

}
