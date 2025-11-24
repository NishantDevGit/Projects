package com.gateway.apigateway;

import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.aayushatharva.brotli4j.Brotli4jLoader;

@SpringBootApplication
@ComponentScan(basePackages = {"com.gateway"})
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
		Brotli4jLoader.ensureAvailability();
	}
	
	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatVirtualThreadCustomizer() {
	    return factory -> factory.addConnectorCustomizers(connector -> {
	        connector.getProtocolHandler().setExecutor(Executors.newVirtualThreadPerTaskExecutor());
	    });
	}
	

}
