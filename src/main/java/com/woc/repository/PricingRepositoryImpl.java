package com.woc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.woc.entity.Pricing;

@Repository
public class PricingRepositoryImpl implements PricingRepository{

	
    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<Pricing> findAll() {
		List<Pricing> prices = entityManager.createNamedQuery("Pricing.findAll").getResultList();
		return prices;
	}
}
