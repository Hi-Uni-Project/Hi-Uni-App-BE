package com.project.hiuni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HiuniApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiuniApplication.class, args);
	}

}
