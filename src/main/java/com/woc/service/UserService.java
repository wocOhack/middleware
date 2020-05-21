package com.woc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woc.entity.User;
import com.woc.repository.UserRepository;

@Component
public class UserService {
	
	@Autowired
	UserRepository userRepo;

	public List<User> getAllUsers(){
		return userRepo.findAll();
	}



}
