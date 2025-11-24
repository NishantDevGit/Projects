package com.gateway.apigateway.apiservice;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.RouteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.apigateway.controller.model.APIService;


import jakarta.annotation.PostConstruct;

@Service
public class APIServiceService {

	@Autowired
	private APIServiceRepo apiServiceRepo;
	
	 @Autowired
	 private CamelContext camelContext;
	 
	 private static final Logger LOGGER = LoggerFactory.getLogger(APIServiceService.class);
	 
	 @PostConstruct
	 private void init() {
		 List<APIService> apiServices = getAllServices();
		 for (APIService apiService : apiServices) {
			 try {
			addRoute(apiService);
			 }catch (Exception e) {
				 LOGGER.error(e.getMessage(),e);
			}
		}
	 }
	
	public void addRoute(APIService service) throws Exception {
		 String routeId = service.getServiceId();

	        camelContext.addRoutes(new RouteBuilder() {
	            @Override
	            public void configure() throws Exception {
	                from("jetty:http://0.0.0.0:8080/" + service.getUri()+"?matchOnUriPrefix=true")
	                    .routeId(routeId)
	                    .setProperty("originalRouteId", simple("${routeId}"))
	                    .to("direct:prerequest")
	                    .toD(service.getBackend()+ "/${header.CamelHttpPath}?bridgeEndpoint=true&throwExceptionOnFailure=false")
	                    .to("direct:postrequest")
	                    .wireTap("seda:logEvent");
	            }
	        });
	        
	        apiServiceRepo.save(service);
	}
	
	public boolean deleteRoute(String apiId) throws Exception {
		if(apiId !=null && !apiId.isEmpty() && camelContext.getRoute(apiId) != null) {
		 org.apache.camel.spi.RouteController controller =  camelContext.getCamelContextExtension().getInternalRouteController();
		 controller.stopRoute(apiId);
		 boolean removed = camelContext.removeRoute(apiId);
		 if(removed) apiServiceRepo.deleteById(apiId);
		 return removed;
		}else return false;
		
		
	}
	
	public List<APIService> getAllServices(){
		return apiServiceRepo.findAll();
	}

}
