package com.woc.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.woc.entity.Pricing;
import com.woc.entity.User;
import com.woc.service.WOCService;


@RestController  
@EnableAutoConfiguration
@RequestMapping("/woc")
public class MainController {
		
	@Autowired
	WOCService wocService;

	@GetMapping("/greet")
	@ResponseBody
	public String hello() {
		return "Greetings!";
	}
	
	@GetMapping("/users")
	@ResponseBody
	public Iterable<User> getUsers() {
		return wocService.getAllUsers();
	}
	
	@GetMapping("/prices")
	@ResponseBody
	public Iterable<Pricing> getPrices(){
		return wocService.getAllPrices();
	}
	
	
}