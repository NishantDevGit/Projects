package com.gateway.route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aayushatharva.brotli4j.Brotli4jLoader;
import com.aayushatharva.brotli4j.decoder.BrotliInputStream;
import com.gateway.apigateway.Message.APIMessage;
import com.gateway.apigateway.Message.APIMessageRepo;

@Component
public class AsyncLogProcessor implements Processor {

	@Autowired
	private APIMessageRepo apiMessageRepo;

	@Override
	public void process(Exchange exchange) {
		Long start = exchange.getProperty("startTimestamp", Long.class);
		Long end = exchange.getProperty("endTimestamp", Long.class);
		long duration = (end != null && start != null) ? (end - start) : -1;
		String serviceId = exchange.getProperty("originalRouteId", String.class);
		String requestBody =  exchange.getProperty("requestBody",String.class);
		String responseBody = null;
		String uri = exchange.getProperty("requestUri", String.class);
		String httpStatus =  exchange.getProperty("httpStatus", String.class);
		String requestId = exchange.getProperty("requestId", String.class);
		String httpMethod = exchange.getProperty("requestMethod", String.class);
		String contentType = exchange.getMessage().getHeader(Exchange.CONTENT_TYPE, String.class);
		
		if(isTextual(contentType)) {
			try {
			String encoding = exchange.getMessage().getHeader("Content-Encoding", String.class);
			if (encoding  !=null &&( encoding.contains("gzip") || encoding.contains("deflate") )) {
			  InputStream gzipStream = exchange.getMessage().getBody(InputStream.class);
			    GZIPInputStream gis;
					gis = new GZIPInputStream(gzipStream);
			     responseBody = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8))
			        .lines().collect(Collectors.joining("\n"));
			}
			else if (encoding  !=null && encoding.contains("br") ) {
				InputStream brStream = exchange.getMessage().getBody(InputStream.class);
				BrotliInputStream bis = new BrotliInputStream(brStream);
				responseBody = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8))
				    .lines().collect(Collectors.joining("\n"));
			}
			else {
				responseBody = exchange.getIn().getBody(String.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseBody ="";
		}
		}else {
			responseBody = "**MASKED**";
		}
		
		String requestHeaders = exchange.getProperty("requestHeaders",String.class);
		String responseHeaders = exchange.getProperty("responseHeaders",String.class);
		
		

		APIMessage apiMessage = new APIMessage(requestId, new Date(start), new Date(end), httpMethod, "",
				requestHeaders, responseHeaders, requestBody, responseBody, httpStatus, duration, serviceId, uri);
		apiMessageRepo.save(apiMessage);
	}
	
	 private boolean isTextual(String contentType) {
	        return contentType != null && (
	            contentType.contains("text") ||
	            contentType.contains("json") ||
	            contentType.contains("xml") ||
	            contentType.contains("html") ||
	            contentType.contains("x-www-form-urlencoded") ||
	            contentType.contains("javascript")
	        );
	    }
	 
	

}
