package com.api.smartfarm;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
@EnableScheduling
public class SmartfarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartfarmApplication.class, args);
	}
	
}
