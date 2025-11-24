package com.bookmymovie.servicediscover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServicediscoverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicediscoverApplication.class, args);
	}

}
