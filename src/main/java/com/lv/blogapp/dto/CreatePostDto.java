package com.lv.blogapp.dto;




import org.springframework.beans.factory.annotation.Autowired;

import com.lv.blogapp.Entity.UserPrincipal;

import lombok.Data;

@Data
public class CreatePostDto {


    
    

    private String title;
    private String content;
   // ID of the user creating the post
}
