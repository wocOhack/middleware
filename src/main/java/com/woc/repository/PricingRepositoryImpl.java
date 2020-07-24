package com.woc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	@Override
	public Pricing getPricingByName(String city) {
		Query q = entityManager.createNativeQuery("select * from Pricing p where p.city=:city", Pricing.class);
		q.setParameter("city", city);
		List<Pricing> pricings = q.getResultList();
		if(pricings == null || pricings.size() < 1) {
			List<Pricing> defaultPricingModel = entityManager.createNativeQuery("select * from Pricing p where p.city='default'", Pricing.class).getResultList();
			if(defaultPricingModel == null || defaultPricingModel.size() < 1) {
				return null;
			} else {
				return defaultPricingModel.get(0);
			}
		}
		return pricings.get(0);
	}
}
