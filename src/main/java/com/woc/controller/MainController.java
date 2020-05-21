package com.woc.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.woc.entity.User;
import com.woc.service.UserService;  

@RestController
public class MainController {
	
@Autowired
UserService userService;

	@GetMapping("/woc")
	@ResponseBody
	public String hello() {
		return "i am first 200 from woc";
	}
	
	@GetMapping("/users")
	@ResponseBody
	public List<User> getUsers() {
		return userService.getAllUsers();
	}
	

}
