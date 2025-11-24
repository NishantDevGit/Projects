package com.bookmymovie.usermanagement.user.service;

import com.bookmymovie.usermanagement.user.User;
import com.bookmymovie.usermanagement.user.UserSecurity;
import com.bookmymovie.usermanagement.user.repo.UserRepo;
import com.bookmymovie.usermanagement.user.repo.UserSecurityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserSecurityRepo userSecurityRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user= userRepo.findByEmailId(username);

       if(user == null)
       {
           throw new UsernameNotFoundException("User Not Found '"+username+"'");
       }

      Optional<UserSecurity> userSecurityOptional = userSecurityRepo.findById(user.getUserId());
       if (userSecurityOptional.isEmpty())
       {
           throw new UsernameNotFoundException("User Not Found '"+username+"'");
       }
       UserSecurity userSecurity = userSecurityOptional.get();
       UserAuth userAuth = new UserAuth(user.getEmailId(),userSecurity.getPassword());
        userAuth.setRole(user.getRole());
        return new UserAuthPrincipal(userAuth);
    }

}
