package com.senla.controller;

import com.senla.dto.PostDto;
import com.senla.entity.Post;
import com.senla.entity.User;
import com.senla.exception.RestError;
import com.senla.facade.PostFacade;
import com.senla.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts/")
@Slf4j
public class PostController {

    private final PostFacade postFacade;
    private final UserFacade userFacade;

    @Autowired
    public PostController(PostFacade postFacade, UserFacade userFacade) {
        this.postFacade = postFacade;
        this.userFacade = userFacade;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postDtoList = postFacade.getAllPosts();
        log.info("You have got all posts successfully");
        if (postDtoList.isEmpty()) {
            log.info("no posts");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "add")
    public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto) {
        postFacade.addPost(postDto);
        log.info("You have added post successfully");
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        List<Post> posts = postFacade.getPostsByUser_Id(id);
        for (Post p : posts) {
            if (p.getId() == id) {
                postFacade.deletePost(id);
                return ResponseEntity.ok()
                        .body("You have deleted the post successfully");

            }
        }

        throw new RestError("You can't delete someone else's post");

        }

        @PostMapping(value = "update")
        public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto){
            User user = userFacade.getUserFromSecurityContext();
            List<Post> posts = postFacade.getPostsByUser_Id(user.getId());
            for (Post p : posts) {
                if (p.getId() == postDto.getId()) {
                    postFacade.updatePost(postDto);
                    log.info("You have updated post successfully");
                }
            }
           throw new RestError("You are trying to update someone else post");
        }

        @GetMapping(value = "search/user/{id}")
        public ResponseEntity<List<PostDto>> getPostByUser_Id (@PathVariable(name = "id") long id){
            List<PostDto> postDtoList = postFacade.getPostsDtoByUser_Id(id);
            log.info("You received the post by user id");
            return new ResponseEntity<>(postDtoList, HttpStatus.OK);
        }

        @GetMapping(value = "{id}")
        public ResponseEntity<PostDto> getPostById (@PathVariable(name = "id") Long postId){
            PostDto postDto = postFacade.getPost(postId);
            log.info("You you got a post by id");
            return new ResponseEntity<>(postDto, HttpStatus.OK);
        }

    }
