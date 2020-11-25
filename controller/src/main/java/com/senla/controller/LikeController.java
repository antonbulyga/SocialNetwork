package com.senla.controller;

import com.senla.converters.LikeDtoToLike;
import com.senla.converters.LikeToLikeDto;
import com.senla.dto.LikeDto;
import com.senla.entity.Like;
import com.senla.entity.User;
import com.senla.exception.RestError;
import com.senla.service.LikeService;
import com.senla.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/likes/")
@Slf4j
public class LikeController {
    private LikeService likeService;
    private LikeToLikeDto likeToLikeDto;
    private LikeDtoToLike likeDtoToLike;
    private UserService userService;

    @Autowired
    public LikeController(LikeService likeService, LikeToLikeDto likeToLikeDto, LikeDtoToLike likeDtoToLike, UserService userService) {
        this.likeService = likeService;
        this.likeToLikeDto = likeToLikeDto;
        this.likeDtoToLike = likeDtoToLike;
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<LikeDto>> getAllUserLikes(){
        User user = userService.getUserFromSecurityContext();
        List<Like> userLikeList = user.getLikes();
        List<LikeDto> likeDtoList = new ArrayList<>();
        if(userLikeList == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (int i = 0; i < userLikeList.size(); i++) {
            LikeDto result = likeToLikeDto.convert(userLikeList.get(i));
            likeDtoList.add(result);
        }
        return new ResponseEntity<>(likeDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "add")
    public ResponseEntity<LikeDto> addLike(@RequestBody LikeDto likeDto) {
        Like like = likeDtoToLike.convert(likeDto);
        likeService.addLike(like);
        return new ResponseEntity<>(likeDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deleteLike(@RequestParam (name = "id") long id) {
        Like like = likeService.getLike(id);
        User user = userService.getUserFromSecurityContext();
        List<Like> userLikes = user.getLikes();
        for(Like l : userLikes){
            if(l.getId() == like.getId()){
                likeService.delete(id);
                return ResponseEntity.ok()
                        .body("You have deleted like successfully");
            }
            else {
                log.error("You are trying to get messages from someone else like");
                throw new RestError("You are trying to get messages from someone else like");
            }

        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

  /*  @GetMapping(value = "{id}")
    public ResponseEntity<LikeDto> getLikeById(@PathVariable(name = "id") Long likeId) {
        Like like = likeService.getLike(likeId);
        LikeDto likeDto = likeToLikeDto.convert(like);
        return new ResponseEntity<>(likeDto, HttpStatus.OK);
    }*/   // что с ним делать????

    @GetMapping(value = "post/id")
    public ResponseEntity<List<LikeDto>> getLikesByPost_Id(@RequestParam (name = "postId") Long postId) {
        List<Like> likes = likeService.getLikesByPost_Id(postId);
        List<LikeDto> dtoList = likes.stream().map(like -> likeToLikeDto.convert(like)).collect(Collectors.toList());

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
