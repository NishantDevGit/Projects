package com.gateway.apigateway.controller.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class APIService {

	@Id
	private String serviceId;
	private String uri;
	private String backend;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getBackend() {
		return backend;
	}
	public void setBackend(String backend) {
		this.backend = backend;
	}
	
	

}
