package com.woc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.woc.entity.Pricing;

@Repository
public interface PricingRepository extends CrudRepository<Pricing, Long>{

}
