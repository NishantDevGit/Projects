package com.gateway.apigateway.user;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {

	@Autowired
	private UserRepo repo;

	private static final ConcurrentHashMap<String, String> userTokenMap = new ConcurrentHashMap<String, String>();

	private static final ConcurrentHashMap<String, List<String>> serviceUserMap = new ConcurrentHashMap<String, List<String>>();

	@PostConstruct
	public void init() {
		List<User> users = repo.findAll();
		for (User user : users) {
			userTokenMap.put(user.getToken(), user.getUsername());
		}
	}

	public void addUser(User user) {

		if (userTokenMap.contains(user.getToken())) {
			throw new RuntimeException("Invalid Token");
		}

		if (repo.findById(user.getUsername()).isPresent())
			throw new RuntimeException("User Already Exist");

		userTokenMap.put(user.getToken(), user.getUsername());
		repo.save(user);
	}
	
	public boolean isValid(String token) {
		
		if(token != null && !token.isEmpty()) {
			if(userTokenMap.contains(token)) return true;
		}
		return false;
	}
	
	public String getUserNameByToken(String token) {
		return userTokenMap.get(token);
	}

	public GetUserResponse getAllUsers() {
		GetUserResponse getUserResponse = new GetUserResponse();
		getUserResponse.addUserNames(new ArrayList<>(userTokenMap.values()));
		return getUserResponse;
	}

}
