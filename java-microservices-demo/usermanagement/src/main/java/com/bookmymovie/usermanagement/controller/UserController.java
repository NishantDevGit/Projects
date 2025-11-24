package com.bookmymovie.usermanagement.controller;

import com.bookmymovie.usermanagement.user.User;
import com.bookmymovie.usermanagement.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService  userService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsersBasicDetails();
    }

    @PostMapping
    public ResponseEntity<Response> addUser(@RequestBody AddUserRequest  addUserRequest){
        Response response = userService.addUser(addUserRequest);
        return  new ResponseEntity<Response>(response, response.getHttpStatusCode(null));
    }

    @PutMapping
    public ResponseEntity<Response> updateUser(@RequestBody UpdateUserRequest updateUserRequest,@RequestHeader(name = "emailId")String emailId ){
        Response response = userService.updateUser(updateUserRequest,emailId);
        return  new ResponseEntity<Response>(response,response.getHttpStatusCode(null));
    }

    @DeleteMapping
    public ResponseEntity<Response> deleteUser(@RequestHeader String emailId){
        Response response = userService.deleteUser(emailId);
        return  new ResponseEntity<Response>(response,response.getHttpStatusCode(null));
    }

    @PostMapping("/auth")
    public ResponseEntity<Response> addUserAuth(@RequestBody UserAuth userAuth){
        Response response = userService.addUserAuth(userAuth);
        return  new ResponseEntity<Response>(response,response.getHttpStatusCode(null));
    }


}
