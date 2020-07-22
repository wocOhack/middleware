package com.woc.repository;

import com.woc.entity.UserLocation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserLocationRepositoryImpl implements UserLocationRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserLocation> findAll() {
        List<UserLocation> userLocationList = entityManager.createNamedQuery("UserLocation.findAll").getResultList();
        return userLocationList;
    }
}
