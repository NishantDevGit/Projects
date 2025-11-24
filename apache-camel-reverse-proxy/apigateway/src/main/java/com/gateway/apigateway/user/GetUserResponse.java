package com.gateway.apigateway.user;

import java.util.ArrayList;
import java.util.List;

public class GetUserResponse {

	private Integer total;
	private List<String> userNames = new ArrayList<String>();

	public Integer getTotal() {
		this.total = userNames.size();
		return total;
	}

	public List<String> getUserNames() {
		return userNames;
	}

	public void addUserNames(List<String> userName) {
		this.userNames.addAll(userName);
	}

}
