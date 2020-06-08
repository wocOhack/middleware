package com.woc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woc.entity.User;
import com.woc.repository.UserRepository;

@Component
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	
	public User createUser(User newUser) {
		return userRepository.createNewUser(newUser);
	}
	
	UserService(){
		super();
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	
	
	
}
