package com.bookmymovie.usermanagement.user.service;

import com.bookmymovie.usermanagement.user.User;
import com.bookmymovie.usermanagement.user.UserSecurity;
import com.bookmymovie.usermanagement.user.repo.UserRepo;
import com.bookmymovie.usermanagement.user.repo.UserSecurityRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserSecurityRepo userSecurityRepo;
    private  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public List<User> getUsersBasicDetails() {
        List<User> users = userRepo.findAll();
        return users;
    }

    @Override
    public Response addUser(AddUserRequest addUserRequest) {
        Response response;
        try{
            LOGGER.info("addUserRequest : "+addUserRequest.toString());
           String id =  UUID.randomUUID().toString();
            User user = new User();
            user.setUserId(id);
            user.setUserName(addUserRequest.getUserName());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dob = dateFormat.parse(addUserRequest.getDob());
            Date createdAt = new Date();
            user.setDob(dob);
            user.setCreatedAt(createdAt);
            user.setRole(addUserRequest.getRole());
            user.setMobile(addUserRequest.getMobile());
            user.setEmailId(addUserRequest.getEmailId());
            userRepo.save(user);
            response = new Response();
            response.setStatus("SUCCESSFUL");
            response.setOperation("ADD_USER");
            response.setDiscription("User added with userId '"+ id +"'");
            response.setHttpCode(201);
            LOGGER.info("User Added with userId "+id);
        }catch (Exception exception){
            LOGGER.error("unable to add user : "+ exception.getMessage(),exception);
            response = new Response();
            response.setStatus("FAILED");
            response.setOperation("ADD_USER");
            response.setDiscription(exception.getMessage());
            response.setHttpCode(400);
        }
        return response;
    }

    @Override
    public Response addUserAuth(UserAuth userAuth) {
        Response response = new Response();
        try{
            User user = userRepo.findByEmailId(userAuth.getEmailId());
            String encodedPassword =   encoder.encode(userAuth.getPassword());
            userSecurityRepo.save(new UserSecurity(user.getUserId(), encodedPassword));
           response.setStatus("SUCCESSFUL");
           response.setOperation("ADD_USER_AUTH");
           response.setDiscription("User Authentication Added For email : "+userAuth.getEmailId());
            response.setHttpCode(201);
        }catch (Exception e){
            LOGGER.error("Failed to Add User Authentication  : "+ e.getMessage(),e);
            response = new Response();
            response.setStatus("FAILED");
            response.setOperation("ADD_USER_AUTH");
            response.setOperation(e.getMessage());
            response.setHttpCode(400);
        }
        return response;
    }

    @Override
    public Response deleteUser(String emailId) {
        Response response = new Response();
        try{
           User user =  userRepo.findByEmailId(emailId);
           userRepo.deleteById(user.getUserId());
           Boolean exists = userSecurityRepo.existsById(user.getUserId());
           if (exists) userSecurityRepo.deleteById(user.getUserId());
           response.setStatus("SUCCESSFUL");
           response.setOperation("DELETE_USER");
           response.setDiscription("User Deleted '"+emailId+"'");
            response.setHttpCode(202);
           LOGGER.info("User Deleted '"+emailId+"'");
        }catch (Exception e){
            response.setStatus("FAILED");
            response.setOperation("DELETE_USER");
            response.setOperation(e.getMessage());
            response.setHttpCode(400);
            LOGGER.error("Unable to delete user '"+emailId+"' : " +e.getMessage(),e);
        }
        return response;
    }

    @Override
    public Response updateUser(UpdateUserRequest updateUserRequest,String emailId) {
        Response response = new Response();
        try{
            User user = userRepo.findByEmailId(emailId);
            if (updateUserRequest.getUserName() != null ) user.setUserName(updateUserRequest.getUserName());
            if (updateUserRequest.getMobile() != null ) user.setMobile(updateUserRequest.getMobile());
            if (updateUserRequest.getRole() != null ) user.setRole(updateUserRequest.getRole());
            if (updateUserRequest.getEmailId() != null ) user.setEmailId(updateUserRequest.getEmailId());
            if (updateUserRequest.getDob() != null){
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dob = dateFormat.parse(updateUserRequest.getDob());
                user.setDob(dob);
            }
            user.setLastUpdatedAt(new Date());
            userRepo.save(user);
            response.setStatus("SUCCESSFUL");
            response.setOperation("UPDATE_USER");
            response.setDiscription("User updated '"+emailId+"'");
            response.setHttpCode(200);
        }catch (Exception e){
            response.setStatus("FAILED");
            response.setOperation("DELETE_USER");
            response.setOperation(e.getMessage());
            response.setHttpCode(400);
            LOGGER.error("Unable to update user '"+emailId+"' : " +e.getMessage(),e);
        }
        return response;
    }
}
