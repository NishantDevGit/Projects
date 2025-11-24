package com.bookmymovie.usermanagement.user.service;

import jakarta.persistence.Column;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddUserRequest {

    private String userName;
    private String mobile;
    private String emailId;
    private String dob;
    private String role;

    @Override
    public String toString() {
        return "AddUserRequest{" +
                "userName='" + userName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", emailId='" + emailId + '\'' +
                ", dob='" + dob + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
