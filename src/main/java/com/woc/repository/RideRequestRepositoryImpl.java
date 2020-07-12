package com.woc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woc.entity.RideRequest;
import com.woc.entity.Rider;
import com.woc.entity.User;

@Repository
public class RideRequestRepositoryImpl implements RideRequestRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RideRequest> findAll() {
		List<RideRequest> riders = entityManager.createNamedQuery("RideRequest.findAll", RideRequest.class)
				.getResultList();
		return riders;
	}

	@Override
	public RideRequest findById(long id) {
		try {
			RideRequest rideRequest = entityManager.createNamedQuery("RideRequest.findById", RideRequest.class)
					.setParameter(1, id).getSingleResult();
			return rideRequest;
		} catch (NoResultException exception) {
			return null;
		}

	}
	
	@Override
	public RideRequest findByRider(Rider rider) {
		try {
			RideRequest rideRequest = entityManager.createNamedQuery("RideRequest.findByRider", RideRequest.class)
					.setParameter(1, rider).getSingleResult();
			return rideRequest;
		} catch (NoResultException exception) {
			return null;
		}

	}

	@Transactional
	@Override
	public void addRideRequest(RideRequest rideRequest) {
		entityManager.persist(rideRequest);
	}
		
	@Transactional
	@Override
	public void deleteRideRequest(RideRequest rideRequest) {
		entityManager.remove(rideRequest);
	}
	
	@Transactional
	@Override
	public void updateRideRequest(RideRequest rideRequest) {
		entityManager.merge(rideRequest);
	}
	
}
