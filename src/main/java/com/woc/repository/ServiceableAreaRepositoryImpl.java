package com.woc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woc.entity.ServiceableArea;
import com.woc.entity.User;

@Repository
public class ServiceableAreaRepositoryImpl implements ServicableAreaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ServiceableArea> findAll() {
        List<ServiceableArea> areas = entityManager.createNamedQuery("ServiceableArea.findAll", ServiceableArea.class).getResultList();
        return areas;
    }

    @Override
    @Transactional
    public ServiceableArea addArea(ServiceableArea r) {
        entityManager.persist(r);
        return r;
    }

}
