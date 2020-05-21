package com.woc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.woc.entity.User;

@Repository
public class UserRepositoryImpl implements UserRepository{


    @PersistenceContext
    EntityManager entityManager;
    @Override
	public List<User> findAll() {
		
    	Query query = entityManager.createNativeQuery("SELECT * FROM USER");
    	return query.getResultList();
	}
}
