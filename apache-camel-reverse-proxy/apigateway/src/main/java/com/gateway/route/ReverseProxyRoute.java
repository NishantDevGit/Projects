package com.gateway.route;

import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReverseProxyRoute extends RouteBuilder {
	
	 @Autowired
	 private AsyncLogProcessor asyncLogProcessor;
	 
	 @Autowired
	 private BearerTokenAuthProcessor authProcessor;

    @Override
    public void configure() {
    	  getContext().setStreamCaching(true);
    	  
    	  onException(UnauthorizedException.class)
    	    .handled(true)
    	    .log("Unauthorized access attempt")
    	    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401))
    	    .setHeader("Content-Type", constant("text/plain"))
    	    .setBody(constant("Unauthorized"))
    	    .setProperty("endTimestamp").method(System.class, "currentTimeMillis")
            .setProperty("responseHeaders", simple("${headers}"))
            .setProperty("httpStatus", header(Exchange.HTTP_RESPONSE_CODE))
            .setHeader("X-Request-ID").exchangeProperty("requestId")
            .removeHeaders("CamelHttp*")
    	    .wireTap("seda:logEvent");
    	
    	  from("seda:logEvent")
    	  .threads(5,20)
          .process(asyncLogProcessor); // custom processor here
    	  
    	  from("direct:prerequest")
    	  .setProperty("startTimestamp").method(System.class, "currentTimeMillis")
    	  .setProperty("requestId").method(UUID.class, "randomUUID")
          // Remove /api prefix
          .setHeader("CamelHttpPath", simple("${header.CamelHttpPath.replaceFirst('/api-example-dev/1','')}"))
          .setProperty("requestUri", header(Exchange.HTTP_URI))
          .setProperty("requestMethod", header(Exchange.HTTP_METHOD))
          .convertBodyTo(String.class)
          .setProperty("requestBody", simple("${body}"))
          .setProperty("requestHeaders", simple("${headers}"));
    	  
    	  
    	  from("direct:postrequest")
    	  .setProperty("endTimestamp").method(System.class, "currentTimeMillis")
          .setProperty("responseHeaders", simple("${headers}"))
          .setProperty("httpStatus", header(Exchange.HTTP_RESPONSE_CODE))
          .setHeader("X-Request-ID").exchangeProperty("requestId")
          .removeHeaders("CamelHttp*")
          .setHeader(Exchange.HTTP_RESPONSE_CODE, exchangeProperty("httpStatus"));

        // Logs incoming request
        from("jetty:http://0.0.0.0:8080/api-example-dev/1?matchOnUriPrefix=true")
            .routeId("api-example-dev-1")
            .setProperty("originalRouteId", simple("${routeId}"))
            .to("direct:prerequest")
            // Forward to backend
            .toD("https://dummyjson.com/${header.CamelHttpPath}?bridgeEndpoint=true&throwExceptionOnFailure=false")
            .to("direct:postrequest")
            .wireTap("seda:logEvent");
        
    }
}