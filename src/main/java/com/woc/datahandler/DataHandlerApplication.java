package com.woc.datahandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.woc.controller.MainController;

@SpringBootApplication
public class DataHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainController.class, args);
	}

}
