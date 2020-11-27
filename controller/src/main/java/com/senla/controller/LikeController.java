package com.senla.controller;

import com.senla.converters.LikeDtoToLike;
import com.senla.converters.LikeToLikeDto;
import com.senla.dto.LikeDto;
import com.senla.entity.Like;
import com.senla.entity.User;
import com.senla.exception.RestError;
import com.senla.facade.LikeFacade;
import com.senla.facade.UserFacade;
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

    private final LikeFacade likeFacade;
    private final UserFacade userFacade;

    @Autowired
    public LikeController(LikeFacade likeFacade, UserFacade userFacade) {
        this.likeFacade = likeFacade;
        this.userFacade = userFacade;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<LikeDto>> getAllUserLikes(){
        User user = userFacade.getUserFromSecurityContext();
        List<Like> likeList = user.getLikes();
        if(likeList == null){
            log.error("The user has no likes");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<LikeDto> likeDtoList = likeFacade.convertListLikesToLikeDto(likeList);
        return new ResponseEntity<>(likeDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "add")
    public ResponseEntity<LikeDto> addLike(@RequestBody LikeDto likeDto) {
        likeFacade.addLike(likeDto);
        log.error("Adding like");
        return new ResponseEntity<>(likeDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deleteLike(@RequestParam (name = "id") long id) throws RestError {
        Like like = likeFacade.getLike(id);
        User user = userFacade.getUserFromSecurityContext();
        List<Like> userLikes = user.getLikes();
        for(Like l : userLikes){
            if(l.getId() == like.getId()){
                likeFacade.deleteLike(id);
                log.error("Deleting like");
                return ResponseEntity.ok()
                        .body("You have deleted like successfully");
            }


        }
        log.error("You are trying to get messages from someone else like");
        throw new RestError("You are trying to get messages from someone else like");

    }

  /*  @GetMapping(value = "{id}")
    public ResponseEntity<LikeDto> getLikeById(@PathVariable(name = "id") Long likeId) {
        Like like = likeService.getLike(likeId);
        LikeDto likeDto = likeToLikeDto.convert(like);
        return new ResponseEntity<>(likeDto, HttpStatus.OK);
    }*/   // что с ним делать????

    @GetMapping(value = "post/id")
    public ResponseEntity<List<LikeDto>> getLikesByPost_Id(@RequestParam (name = "postId") Long postId) {
        List<LikeDto> dtoList = likeFacade.getLikesByPost_Id(postId);
        log.info("Getting like by post id");
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
