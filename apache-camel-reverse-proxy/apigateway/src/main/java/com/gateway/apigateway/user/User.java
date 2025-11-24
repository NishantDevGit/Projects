package com.gateway.apigateway.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "users_db")
public class User {
	
	@Id
	private String username;
	private String token;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
