package com.woc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woc.entity.Pricing;
import com.woc.entity.User;
import com.woc.repository.PricingRepository;
import com.woc.repository.UserRepository;


@Service
public class WOCServiceImpl implements WOCService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PricingRepository pricingRepository;


	public Iterable<User> getAllUsers(){
		System.out.println("Reached");
		return userRepository.findAll();
	}
	

	public Iterable<Pricing> getAllPrices(){
		System.out.println("Reached");
		return pricingRepository.findAll();
	}


}
