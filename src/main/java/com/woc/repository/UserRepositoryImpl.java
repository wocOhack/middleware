package com.woc.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.woc.entity.User;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<User> findAll() {
		List<User> users = entityManager.createNamedQuery("User.findAll").getResultList();
		return users;
	}
	
	@Override
	@Transactional 
	public User createNewUser(User newUser) {
		newUser.setRegistrationDate(getCurrentDate());
		entityManager.persist(newUser);
		return newUser;
	}

	private Date getCurrentDate() {
		Date currentTime = new Date(System.currentTimeMillis());
		return currentTime;
	}
    

    @Transactional
    @Override
    public User addUser(User u) {
       entityManager.persist(u);
       return u;
    }

	@Override
	public User findByID(long id) {
		List<User> users = entityManager.createNamedQuery("User.findById").setParameter(1, id).getResultList();
		if(!users.isEmpty()) {
			return users.get(0);
		}
		return null;
		
	}
}
