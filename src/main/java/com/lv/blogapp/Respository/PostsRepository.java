package com.lv.blogapp.Respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lv.blogapp.Entity.Posts;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    // Custom query methods can be defined here if needed
    // For example, to find posts by user:
    // List<Posts> findall();
    List<Posts> findByUserId(Long userId);
    
    // Or to find posts by title:
    // List<Posts> findByTitleContaining(String title);

}
