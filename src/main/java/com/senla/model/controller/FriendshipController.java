package com.senla.model.controller;

import com.senla.model.converters.FriendshipToFriendshipDto;
import com.senla.model.converters.UserToUserDto;
import com.senla.model.dto.FriendshipDto;
import com.senla.model.dto.UserDto;
import com.senla.model.entity.Friendship;
import com.senla.model.entity.User;
import com.senla.model.service.FriendshipService;
import com.senla.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/friendship/")
public class FriendshipController {
    private FriendshipService friendshipService;
    private UserToUserDto userToUserDto;
    private FriendshipToFriendshipDto friendshipToFriendshipDto;

    @Autowired
    public FriendshipController(FriendshipService friendshipService, UserService userService, UserToUserDto userToUserDto, FriendshipToFriendshipDto friendshipToFriendshipDto) {
        this.friendshipService = friendshipService;
        this.userToUserDto = userToUserDto;
        this.friendshipToFriendshipDto = friendshipToFriendshipDto;
    }

    @PostMapping(value = "request/new")
    public ResponseEntity<FriendshipDto> sentNewFriendRequest(@RequestParam(name = "idOne") Long userOneId,
                                                       @RequestParam(name = "idTwo") Long userTwoId,
                                                       @RequestParam(name = "idAction") Long actionUserId ) {

        Friendship friendship = friendshipService.sentNewFriendRequest(userOneId, userTwoId, actionUserId);
        FriendshipDto friendshipDto = friendshipToFriendshipDto.convert(friendship);
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @PostMapping(value = "request")
    public ResponseEntity<FriendshipDto> sentFriendRequest(@RequestParam(name = "idOne") Long userOneId,
                                                    @RequestParam(name = "idTwo") Long userTwoId,
                                                    @RequestParam(name = "idAction") Long actionUserId ) {
        Friendship friendship = friendshipService.sentFriendRequest(userOneId, userTwoId, actionUserId);
        FriendshipDto friendshipDto = friendshipToFriendshipDto.convert(friendship);
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @PostMapping(value = "add")
    public ResponseEntity<FriendshipDto> addToFriends(@RequestParam(name = "idOne") Long userOneId,
                                               @RequestParam(name = "idTwo") Long userTwoId,
                                               @RequestParam(name = "idAction") Long actionUserId)  {
        Friendship friendship = friendshipService.addToFriends(userOneId, userTwoId, actionUserId);
        FriendshipDto friendshipDto = friendshipToFriendshipDto.convert(friendship);
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @GetMapping(value = "friends")
    public ResponseEntity<List<UserDto>> getFriendsListOfUser(@RequestParam(name = "id") Long userId) {

        List<User> users = friendshipService.getFriendsListOfUser(userId);
        List<UserDto> userDto = users.stream().map(user -> userToUserDto.convert(user)).collect(Collectors.toList());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping(value = "delete")
    public ResponseEntity<FriendshipDto> deleteFriendship(@RequestParam(name = "idOne") Long userOneId,
                                                   @RequestParam(name = "idTwo") Long userTwoId,
                                                   @RequestParam(name = "idAction") Long actionUserId){
        Friendship friendship = friendshipService.deleteFriendship(userOneId, userTwoId, actionUserId);
        FriendshipDto friendshipDto = friendshipToFriendshipDto.convert(friendship);
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @PostMapping(value = "block")
    public ResponseEntity<FriendshipDto> blockUser(@RequestParam(name = "idOne") Long userOneId,
                                                   @RequestParam(name = "idTwo") Long userTwoId,
                                                   @RequestParam(name = "idAction") Long actionUserId) {
        Friendship friendship = friendshipService.blockUser(userOneId, userTwoId, actionUserId);
        FriendshipDto friendshipDto = friendshipToFriendshipDto.convert(friendship);
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @PostMapping(value = "unblock")
    public ResponseEntity<FriendshipDto> unblockUser(@RequestParam(name = "idOne") Long userOneId,
                                              @RequestParam(name = "idTwo") Long userTwoId,
                                              @RequestParam(name = "idAction") Long actionUserId) {

        Friendship friendship = friendshipService.unblockUser(userOneId, userTwoId, actionUserId);
        FriendshipDto friendshipDto = friendshipToFriendshipDto.convert(friendship);
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @GetMapping(value = "requests")
    public ResponseEntity<Map<String, List<UserDto>>> getRequests(@RequestParam(name = "id") Long userId) {

        Map<String, List<UserDto>> requests =  friendshipService.getRequest(userId);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }
}
