package com.nexthub;

import com.nexthub.config.NodeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties(NodeProperties.class)
public class NexthubTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexthubTestApplication.class, args);
	}

}
