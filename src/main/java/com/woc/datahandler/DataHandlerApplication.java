package com.woc.datahandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.woc")
@EntityScan("com.woc.entity")
@SpringBootApplication
public class DataHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataHandlerApplication.class, args);
	}

}
