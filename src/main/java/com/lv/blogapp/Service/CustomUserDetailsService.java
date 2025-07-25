package com.lv.blogapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lv.blogapp.Entity.UserPrincipal;
import com.lv.blogapp.Entity.Users;
import com.lv.blogapp.Respository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired  
    private  UserRepository userRepository;

    
     
   
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users user =userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("user not found ");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new UserPrincipal(user); // Assuming UserPrincipal is your implementation of UserDetails
    }



   
    // Additional methods can be added as needed


}
