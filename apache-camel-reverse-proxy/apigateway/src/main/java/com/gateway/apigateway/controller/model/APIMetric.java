package com.gateway.apigateway.controller.model;

import java.util.List;

public class APIMetric {
	
	private String apiId;
	private Long totalCount;
	List<HttpStatusCount> httpStatusCounts;

	public APIMetric() {
		// TODO Auto-generated constructor stub
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public List<HttpStatusCount> getHttpStatusCounts() {
		return httpStatusCounts;
	}

	public void setHttpStatusCounts(List<HttpStatusCount> httpStatusCounts) {
		this.httpStatusCounts = httpStatusCounts;
	}

	
	
	

	

}
