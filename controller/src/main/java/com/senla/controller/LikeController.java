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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "")
    public ResponseEntity<List<LikeDto>> getAllUserLikes() {
        User user = userFacade.getUserFromSecurityContext();
        List<Like> likeList = user.getLikes();
        if (likeList.isEmpty()) {
            log.error("The user has no likes");
            throw new RestError("The user has no likes");
        }
        List<LikeDto> likeDtoList = likeFacade.convertListLikesToLikeDto(likeList);
        return new ResponseEntity<>(likeDtoList, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "add")
    public ResponseEntity<LikeDto> addLike(@Valid @RequestBody LikeDto likeDto) {
        User user = userFacade.getUserFromSecurityContext();
        List<Like> likes = user.getLikes();
        for (Like l:likes){
            if(l.getUser().getId().equals(likeDto.getUser().getId())){
                likeFacade.addLike(likeDto);
                log.error("Adding like");
                return new ResponseEntity<>(likeDto, HttpStatus.OK);
            }
        }
        log.error("You are trying to add like from someone else user");
        throw new RestError("You are trying to add like from someone else user");

    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deleteLike(@RequestParam(name = "id") long id) {
        Like like = likeFacade.getLike(id);
        User user = userFacade.getUserFromSecurityContext();
        List<Like> userLikes = user.getLikes();
        for (Like l : userLikes) {
            if (l.getId().equals(like.getId())) {
                likeFacade.deleteLike(id);
                log.error("Deleting like");
                return ResponseEntity.ok()
                        .body("You have deleted like successfully");
            }

        }
        log.error("You are trying to delete like from someone else user");
        throw new RestError("You are trying to delete like from someone else user");

    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "{id}")
    public ResponseEntity<LikeDto> getLikeById(@PathVariable(name = "id") Long likeId) {
        LikeDto likeDto = likeFacade.getLikeDto(likeId);
        log.info("You are getting like by id");
        return new ResponseEntity<>(likeDto, HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "post/id")
    public ResponseEntity<List<LikeDto>> getLikesByPost_Id(@RequestParam(name = "postId") Long postId) {
        List<LikeDto> dtoList = likeFacade.getLikesByPost_Id(postId);
        if (dtoList.isEmpty()) {
            log.error("The user has no likes or post does't exist");
            throw new RestError("The user has no likes or post does't exist");
        }
        log.info("Getting like by post id");
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
