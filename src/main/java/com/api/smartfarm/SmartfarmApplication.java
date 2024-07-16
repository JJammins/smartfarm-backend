package com.api.smartfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartfarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartfarmApplication.class, args);
	}

}
