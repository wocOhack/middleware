package com.woc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woc.entity.Rider;
import com.woc.entity.User;

@Repository
public class RiderRepositoryImpl implements RiderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Rider> findAll() {
        List<Rider> riders = entityManager.createNamedQuery("Rider.findAll", Rider.class).getResultList();
        return riders;
    }
    
    @Transactional
    @Override
    public void addRider(Rider r) {
        // TODO Auto-generated method stub
        entityManager.persist(r);
    }

}
