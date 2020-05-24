package com.woc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.woc.entity.Rider;

@Repository
public class RiderRepositoryImpl implements RiderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Rider> findAll() {
        List<Rider> riders = entityManager.createNamedQuery("Rider.findAll").getResultList();
        return riders;
    }

}
