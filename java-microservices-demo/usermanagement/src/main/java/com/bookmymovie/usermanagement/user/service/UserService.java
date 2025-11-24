package com.bookmymovie.usermanagement.user.service;

import com.bookmymovie.usermanagement.user.User;

import java.util.List;

public interface UserService {

    List<User> getUsersBasicDetails();

    Response addUser(AddUserRequest addUserRequest);

    Response addUserAuth(UserAuth userAuth);

    Response deleteUser(String emailId);

    Response updateUser(UpdateUserRequest updateUserRequest,String emailId);

}
