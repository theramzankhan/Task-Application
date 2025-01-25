package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
		System.out.println("Hello!");
	}

}


//NEW TASKS TO BE DONE
//1. Make userdto class and show bio, location, etc with name, email, etc
//2. add validations in fields
//3. add loggings
//4. make a file where to declare constants variables
//5. try login using session (HttpServlet)
//6. Make status field as boolean ACTIVE or INACTIVE - done
//7. Pagination
//8. Searching - done
//9. Comment delete, update, etc and all operations