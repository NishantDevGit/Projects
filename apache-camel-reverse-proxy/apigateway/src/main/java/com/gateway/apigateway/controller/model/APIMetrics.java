package com.gateway.apigateway.controller.model;

import java.util.List;

public class APIMetrics {
	
	private Long total;
	private List<APIMetric> apiMetrics;

	public APIMetrics() {
		// TODO Auto-generated constructor stub
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<APIMetric> getApiMetrics() {
		return apiMetrics;
	}

	public void setApiMetrics(List<APIMetric> apiMetrics) {
		this.apiMetrics = apiMetrics;
	}

	
	
}
