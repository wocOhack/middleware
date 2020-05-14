package com.woc.controller;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;  
@RestController  
@EnableAutoConfiguration

public class MainController {

	@RequestMapping("/woc")
	@ResponseBody
	public String hello() {
		return "i am first 200 from woc";
	}

}
