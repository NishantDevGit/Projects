package com.gateway.apigateway.controller.model;

import java.util.List;

import org.springframework.data.domain.Page;

import com.gateway.apigateway.Message.APIMessage;

public class APIMessagesResponse {
	
	private int total;
	
	private List<APIMessage> apiMessages;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<APIMessage> getApiMessages() {
		return apiMessages;
	}

	public void setApiMessages(Page<APIMessage> apiMessages) {
		this.apiMessages = apiMessages.getContent();
		this.total = this.apiMessages.size();
	}
	
	

}
