package com.woc.repository;

import java.util.List;

import com.woc.entity.Pricing;

public interface PricingRepository{

	public List<Pricing> findAll();
}
