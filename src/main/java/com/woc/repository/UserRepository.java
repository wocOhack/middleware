package com.woc.repository;

import java.util.List;

import com.woc.entity.User;

public interface UserRepository{

	List<User> findAll();
	
	public User createNewUser(User newUser);
	
}
