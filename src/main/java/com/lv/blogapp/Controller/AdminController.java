package com.lv.blogapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.lv.blogapp.Entity.Users;
import com.lv.blogapp.Respository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class AdminController {


    @Autowired
    UserRepository userRepository;

    @GetMapping("/admin")
    public String greetadmin() {
        return "hello admin how are you this will be your dashboard";
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<Users>> getUsers() {
        List<Users> users=userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    
    

   

}
