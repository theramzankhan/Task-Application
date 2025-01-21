package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DatabaseConnectivityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseConnectivityApplication.class, args);
		System.out.println("Hello!");
	}

}
