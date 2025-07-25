package com.lv.blogapp.Service;

import com.lv.blogapp.dto.CreatePostDto;
import com.lv.blogapp.dto.PostDto;

public interface PostService {
    PostDto createPost(CreatePostDto createPostDto,Long userId);
    PostDto updatePost(Long postId, CreatePostDto dto, String username);
    void deletePost(Long postId,Long userId );

}
