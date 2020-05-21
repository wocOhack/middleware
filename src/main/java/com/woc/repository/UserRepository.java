package com.woc.repository;
import java.util.List;

import com.woc.entity.User;


public interface UserRepository {
	
	public List<User> findAll();

}
