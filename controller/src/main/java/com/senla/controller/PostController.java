package com.senla.controller;

import com.senla.converters.PostDtoToPost;
import com.senla.converters.PostToPostDto;
import com.senla.dto.PostDto;
import com.senla.entity.Post;
import com.senla.entity.User;
import com.senla.exception.EntityNotFoundException;
import com.senla.service.PostService;
import com.senla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/posts/")
public class PostController {
    private PostService postService;
    private PostToPostDto postToPostDto;
    private PostDtoToPost postDtoToPost;
    private UserService userService;

    @Autowired
    public PostController(PostService postService, PostToPostDto postToPostDto, PostDtoToPost postDtoToPost, UserService userService) {
        this.postService = postService;
        this.postToPostDto = postToPostDto;
        this.postDtoToPost = postDtoToPost;
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostDto> postDtoList = new ArrayList<>();
        if (posts == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (int i = 0; i < posts.size(); i++) {
            PostDto result = postToPostDto.convert(posts.get(i));
            postDtoList.add(result);
        }
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "add")
    public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto) {
        Post post = postDtoToPost.convert(postDto);
        postService.addPost(post);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        try {
            postService.getPost(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.ok()
                    .body("Post does not exist");
        }
        List<Post> posts = postService.getPostsByUser_Id(id);
        for (Post p : posts) {
            if (p.getId() == id) {
                postService.deletePost(id);
                return ResponseEntity.ok()
                        .body("You have deleted the post successfully");

            }
        }
               return ResponseEntity.ok()
                    .body("You can't delete someone else's post");

        }

        @PostMapping(value = "update")
        public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto){
            Post post = postDtoToPost.convert(postDto);
            User user = userService.getUserFromSecurityContext();
            List<Post> posts = postService.getPostsByUser_Id(user.getId());
            for (Post p : posts) {
                if (p.getId() == post.getId()) {
                    postService.editPost(post);
                }
            }
            return new ResponseEntity<>(postDto, HttpStatus.OK);
        }

        @GetMapping(value = "search/user/{id}")
        public ResponseEntity<List<PostDto>> getPostByUser_Id ( @PathVariable(name = "id") long id){
            List<Post> posts = postService.getPostsByUser_Id(id);
            List<PostDto> postDtoList = posts.stream().map(post -> postToPostDto.convert(post)).collect(Collectors.toList());
            return new ResponseEntity<>(postDtoList, HttpStatus.OK);
        }

        @GetMapping(value = "{id}")
        public ResponseEntity<PostDto> getPostById (@PathVariable(name = "id") Long postId){
            Post post = postService.getPost(postId);
            PostDto postDto = postToPostDto.convert(post);
            return new ResponseEntity<>(postDto, HttpStatus.OK);
        }

    }
