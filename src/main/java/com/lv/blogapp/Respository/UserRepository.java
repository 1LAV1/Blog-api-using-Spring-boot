package com.lv.blogapp.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lv.blogapp.Entity.Users;



@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    
    Users findByUsername(String username);
    
   
    
    // Additional query methods can be defined here if needed

}
