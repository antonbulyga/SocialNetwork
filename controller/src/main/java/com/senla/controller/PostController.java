package com.senla.controller;

import com.senla.dto.PostDto;
import com.senla.entity.Post;
import com.senla.entity.User;
import com.senla.exception.EntityNotFoundException;
import com.senla.exception.RestError;
import com.senla.facade.PostFacade;
import com.senla.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postDtoList = postFacade.getAllPosts();
        log.info("You have got all posts successfully");
        if (postDtoList.isEmpty()) {
            log.info("No posts");
            throw new RestError("No posts");
        }
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "add")
    public ResponseEntity<PostDto> addPost(@Valid @RequestBody PostDto postDto) {
        PostDto postDtoWithDate = postFacade.addPost(postDto);
        log.info("You have added post successfully");
        return new ResponseEntity<>(postDtoWithDate, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deletePost(@RequestParam(name = "id") Long id) {
        User user = userFacade.getUserFromSecurityContext();
        List<Post> posts = user.getPosts();
        for (Post p : posts) {
            if (p.getId().equals(id)) {
                postFacade.deletePost(id);
                return ResponseEntity.ok()
                        .body("You have deleted the post successfully");

            }
        }

        log.warn("You can't delete someone else's post");
        throw new RestError("You can't delete someone else's post");

    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping(value = "update")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto) {
        User user = userFacade.getUserFromSecurityContext();
        List<Post> posts = user.getPosts();
        for (Post p : posts) {
            if (p.getId() == postDto.getId()) {
                PostDto postDtoWithDate =  postFacade.updatePost(postDto);
                log.info("You have updated post successfully");
                return new ResponseEntity<>(postDtoWithDate, HttpStatus.OK);
            }
        }
        log.warn("You are trying to update someone else post");
        throw new RestError("You are trying to update someone else post");
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "search/user/{id}")
    public ResponseEntity<List<PostDto>> getPostByUser_Id(@PathVariable(name = "id") Long id) {
        List<PostDto> postDtoList = postFacade.getPostsDtoByUser_Id(id);
        log.info("You received the post by user id");
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long postId) {
        PostDto postDto = postFacade.getPost(postId);
        log.info("You got a post by id");
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

}
