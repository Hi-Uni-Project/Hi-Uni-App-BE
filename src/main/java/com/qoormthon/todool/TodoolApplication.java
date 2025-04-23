package com.qoormthon.todool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TodoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoolApplication.class, args);
	}

}
