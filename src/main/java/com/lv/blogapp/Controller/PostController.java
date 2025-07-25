package com.lv.blogapp.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.lv.blogapp.Entity.Posts;
import com.lv.blogapp.Entity.UserPrincipal;
import com.lv.blogapp.Entity.Users;
import com.lv.blogapp.Respository.PostsRepository;
import com.lv.blogapp.Service.PostService;
import com.lv.blogapp.Service.UserService;
import com.lv.blogapp.dto.CreatePostDto;
import com.lv.blogapp.dto.PostDto;

import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class PostController {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserService Service;
    

   

    @GetMapping("/")
    public String greet() {
        return "THIS IS blog website";
    }
    

    @Transactional
    @GetMapping("/home")
    public List<PostDto> showAllPosts(){
        List<Posts> posts= postsRepository.findAll();

        List<PostDto> postdtos = posts.stream().map(post -> {
            return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getUsername() // Assuming you want the author's username
            );
        }).toList();


        return postdtos;
    }



     @PostMapping("/register")
     public Users register(@RequestBody Users user){
        return Service.register(user);
     }

     @PostMapping("/login")
    public String login(@RequestBody Users user) {
        // Implement login logic here
        System.out.println(user);
        return Service.verify(user);
    }

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody CreatePostDto createPostDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getUser().getId(); 
        PostDto savedPost = postService.createPost(createPostDto,userId);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
        }


    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable Long postId,
            @RequestBody CreatePostDto postDto,
            Principal principal) {

        PostDto updated = postService.updatePost(postId, postDto, principal.getName());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost( @PathVariable Long postId
         ){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal(); // Cast to your custom User class
            Users user = principal.getUser();
            Long userId=user.getId();

            postService.deletePost(postId, userId);
            return ResponseEntity.ok("Post Deleted successfully");

    }



     
}
