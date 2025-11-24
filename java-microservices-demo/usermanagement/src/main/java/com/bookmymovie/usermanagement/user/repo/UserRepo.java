package com.bookmymovie.usermanagement.user.repo;

import com.bookmymovie.usermanagement.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,String> {

    User findByEmailId(String emailId);

}
