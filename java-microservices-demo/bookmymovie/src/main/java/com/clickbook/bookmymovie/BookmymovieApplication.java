package com.clickbook.bookmymovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BookmymovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmymovieApplication.class, args);
	}

}
