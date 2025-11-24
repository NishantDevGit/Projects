package com.gateway.apigateway.Message;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity(name = "API_GATEWAY_MESSAGES")
public class APIMessage {

	@Id
	private String requestId;
	
	private Date startTime;
	private Date endTime;
	private String httpMethod;
	private String callerName;
	//@Lob  // optional, but good for large headers
	@Column(name = "REQUEST_HEADERS", columnDefinition = "TEXT")
	private String requestHeaders;
	//@Lob  // optional, but good for large headers
	@Column(name = "RESPONSE_HEADERS", columnDefinition = "TEXT")
	private String responseHeaders;
	//@Lob
	@Column(columnDefinition = "TEXT")
	private String requestBody;
	//@Lob
	@Column(columnDefinition = "TEXT")
	private String responseBody;
	private String httpstatus;
	private long duration;
	private String serviceId;
	private String requestURI;
	
	
	
	@Override
	public String toString() {
		return "APIMessage [requestId=" + requestId + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", httpMethod=" + httpMethod + ", callerName=" + callerName + ", requestHeaders=" + requestHeaders
				+ ", responseHeaders=" + responseHeaders + ", requestBody=" + requestBody + ", responseBody="
				+ responseBody + ", httpstatus=" + httpstatus + ", duration=" + duration + ", serviceId=" + serviceId
				+ ", requestURI=" + requestURI + "]";
	}
	public APIMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public APIMessage(String requestId, Date startTime, Date endTime, String httpMethod, String callerName,
			String requestHeaders, String responseHeaders, String requestBody, String responseBody, String httpstatus,
			long duration, String serviceId, String requestURI) {
		super();
		this.requestId = requestId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.httpMethod = httpMethod;
		this.callerName = callerName;
		this.requestHeaders = requestHeaders;
		this.responseHeaders = responseHeaders;
		this.requestBody = requestBody;
		this.responseBody = responseBody;
		this.httpstatus = httpstatus;
		this.duration = duration;
		this.serviceId = serviceId;
		this.requestURI = requestURI;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
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
	public String getRequestHeaders() {
		return requestHeaders;
	}
	public void setRequestHeaders(String requestHeaders) {
		this.requestHeaders = requestHeaders;
	}
	public String getResponseHeaders() {
		return responseHeaders;
	}
	public void setResponseHeaders(String responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
	public String getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	public String getHttpstatus() {
		return httpstatus;
	}
	public void setHttpstatus(String httpstatus) {
		this.httpstatus = httpstatus;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	
	
	
}
