package com.woc.repository;

import com.woc.dto.TripSearchCriteria;
import com.woc.entity.Trip;
import com.woc.entity.User;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
                    .createNativeQuery("select * from Trip t where t.User.id = " + id, Trip.class).getResultList();
            if (trips.size() == 0) {
                return null;
            }
            return trips;
        }
        if (searchCriteria.getDriverID() != 0) {
            long id = searchCriteria.getDriverID();
            List<Trip> trips = entityManager
                    .createNativeQuery("select * from Trip t where t.User.id = " + id, Trip.class).getResultList();
            if (trips.size() == 0) {
                return null;
            }
            return trips;
        }
        return null;
    }

}
