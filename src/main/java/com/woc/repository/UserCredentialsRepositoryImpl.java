package com.woc.repository;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woc.entity.UserCredentials;

@Repository
public class UserCredentialsRepositoryImpl implements UserCredentialsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addUserCredentails(UserCredentials u) {
        // TODO Auto-generated method stub
        u.setCreated_time(getCurrentDate());
        entityManager.persist(u);
    }

    private Date getCurrentDate() {
        Date currentTime = new Date(System.currentTimeMillis());
        return currentTime;
    }

}
