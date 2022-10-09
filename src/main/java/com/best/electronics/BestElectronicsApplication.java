package com.best.electronics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BestElectronicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BestElectronicsApplication.class, args);
		System.out.println(firstMethod());
	}

	public static String firstMethod() {
		return "Best Electronics";
	}
}
