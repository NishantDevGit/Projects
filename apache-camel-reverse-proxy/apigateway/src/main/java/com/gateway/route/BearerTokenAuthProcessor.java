package com.gateway.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gateway.apigateway.user.UserService;

@Component
public class BearerTokenAuthProcessor implements Processor {

	@Autowired
	private UserService userService;

    @Override
    public void process(Exchange exchange) {
        String authHeader = exchange.getIn().getHeader("Authorization", String.class);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            unauthorized(exchange);
            return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();
        if (!userService.isValid(token)) {
            unauthorized(exchange);
        }else {
        	exchange.setProperty("API-GATEWAY-USERNAME", userService.getUserNameByToken(token)+"");
        }
    }

    private void unauthorized(Exchange exchange) {
    	 throw new UnauthorizedException("Invalid or missing token");
    }
}