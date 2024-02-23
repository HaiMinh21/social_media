package com.epu.controller;

import com.epu.models.Post;
import com.epu.models.User;
import com.epu.response.ApiResponse;
import com.epu.service.PostService;
import com.epu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String jwt, @RequestBody Post post) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Post createPost = postService.createNewPost(post, reqUser.getId());
        return new ResponseEntity<>(createPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        String message = postService.deletePost(postId, reqUser.getId());
        ApiResponse response = new ApiResponse(message, true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws Exception {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/posts/user/{userId}")
    public ResponseEntity<List<Post>> findUserPost(@PathVariable Integer userId){
        List<Post> posts = postService.findPostByUserId(userId);
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> findAllPost(){
        List<Post> posts = postService.findAllPost();
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @PutMapping("/api/posts/save/{postId}")
    public ResponseEntity<Post> savedPostHandler(
            @PathVariable Integer postId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.savedPost(postId, reqUser.getId());
        return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/posts/like/{postId}")
    public ResponseEntity<Post> likePostHandler(
            @PathVariable Integer postId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.likePost(postId, reqUser.getId());
        return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
    }
}
