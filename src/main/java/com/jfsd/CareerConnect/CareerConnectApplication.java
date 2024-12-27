package com.jfsd.CareerConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins ="*")

public class CareerConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerConnectApplication.class, args);
	}

}
