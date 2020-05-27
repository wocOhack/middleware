package com.woc.repository;

import com.woc.entity.Trip;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TripRepositoryImpl implements TripRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Trip> findAll() {
        List<Trip> trips = entityManager.createNamedQuery("Trip.findAll").getResultList();
        return trips;
    }
}
