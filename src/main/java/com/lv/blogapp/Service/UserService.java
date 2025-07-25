package com.lv.blogapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lv.blogapp.Entity.Users;
import com.lv.blogapp.Respository.UserRepository;


@Service
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;



    @Autowired
    JwtService jwtservice;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder( 12);



    public Users register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);   
    }



    public String verify(Users user) {
       Authentication authentication= authenticationManager
                                            .authenticate(new UsernamePasswordAuthenticationToken(
                                                user.getUsername(),
                                                user.getPassword()
                                            ));
        if(authentication.isAuthenticated()){

            System.out.println("User authenticated successfully");
            return jwtservice.generatetoken(user.getUsername());
        }
        return "failed to authenticate user";
    
    
    
                                        }




}
