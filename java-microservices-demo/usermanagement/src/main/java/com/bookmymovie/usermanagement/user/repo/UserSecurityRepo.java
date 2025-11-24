package com.bookmymovie.usermanagement.user.repo;

import com.bookmymovie.usermanagement.user.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecurityRepo extends JpaRepository<UserSecurity,String> {
}
