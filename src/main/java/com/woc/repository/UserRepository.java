package com.woc.repository;

import java.util.List;

import com.woc.entity.User;

public interface UserRepository {

	public List<User> findAll();
	
	public User createNewUser(User newUser);
	
	public User addUser(User u);

	public User findByID(long id);

    public long updateUser(String name, String email, String phone, long userID);
}
