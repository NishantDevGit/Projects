package com.bookmymovie.usermanagement.user.service;

import org.springframework.http.HttpStatus;

public class Response {

    private String status;
    private String operation;
    private String discription;
    private int httpCode;

    public Response() {
    }

    public Response(String status, String operation, String discription) {
        this.status = status;
        this.operation = operation;
        this.discription = discription;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                ", operation='" + operation + '\'' +
                ", discription='" + discription + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public HttpStatus getHttpStatusCode(Integer value){

        if (value == null){
            return HttpStatus.resolve(httpCode);
        }
        else
        {
            return  HttpStatus.resolve(value);
        }
    }

}
