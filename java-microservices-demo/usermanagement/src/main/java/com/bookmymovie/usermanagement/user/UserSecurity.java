package com.bookmymovie.usermanagement.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserSecurity {

    @Id
    private String userId;
    private String password;

    public UserSecurity() {
    }

    public UserSecurity(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
