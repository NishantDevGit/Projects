package com.gateway.apigateway.controller.model;

import java.util.Date;


public class SearchLogQuery {
	
	private Date startTime;
	private Date endTime;
	private String httpMethod;
	private String callerName;
	private String httpStatus;
	private String serviceId;
	private Integer limit;

	public SearchLogQuery() {
		// TODO Auto-generated constructor stub
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getCallerName() {
		return callerName;
	}

	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "SearchLogQuery [startTime=" + startTime + ", endTime=" + endTime + ", httpMethod=" + httpMethod
				+ ", callerName=" + callerName + ", httpStatus=" + httpStatus + ", serviceId=" + serviceId + ", limit="
				+ limit + "]";
	}
	
	

}
