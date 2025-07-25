package com.lv.blogapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lv.blogapp.Entity.Posts;
import com.lv.blogapp.Entity.Role;
import com.lv.blogapp.Entity.Users;
import com.lv.blogapp.Respository.PostsRepository;
import com.lv.blogapp.Respository.UserRepository;
import com.lv.blogapp.dto.CreatePostDto;
import com.lv.blogapp.dto.PostDto;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostsRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PostDto createPost(CreatePostDto dto,Long userId) {
        Users user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));

        Posts post = new Posts();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setUser(user); // setting the entire user object

        Posts saved = postRepository.save(post);

        return new PostDto(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getUser().getUsername()
        );
    }

    @Override
public PostDto updatePost(Long postId, CreatePostDto dto, String username) {
    Posts post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));

    if (!post.getUser().getUsername().equals(username)) {
        throw new RuntimeException("You are not authorized to update this post");
    }

    post.setTitle(dto.getTitle());
    post.setContent(dto.getContent());

    Posts saved = postRepository.save(post);

    return new PostDto(saved.getId(), saved.getTitle(), saved.getContent(), saved.getUser().getUsername());
}


@Override
public void deletePost(Long postId, Long userId) {
     Posts post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));

    Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

      if (!post.getUser().getId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
        throw new RuntimeException("You are not allowed to delete this post");
    }

    postRepository.delete(post);
}

}

