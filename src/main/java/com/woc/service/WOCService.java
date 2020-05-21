package com.woc.service;

import com.woc.entity.Pricing;
import com.woc.entity.User;


public interface WOCService {

	Iterable<User> getAllUsers();

	Iterable<Pricing> getAllPrices();


}
