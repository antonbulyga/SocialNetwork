package com.senla.controller;

import com.senla.converters.FriendshipToFriendshipDto;
import com.senla.converters.UserToUserDto;
import com.senla.dto.FriendshipDto;
import com.senla.dto.UserDto;
import com.senla.entity.Friendship;
import com.senla.entity.User;
import com.senla.service.FriendshipService;
import com.senla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        List<Friendship> requests = friendshipService.getRequest(userId);
        Map<Boolean, List<Friendship>> requestMap = requests.stream()
                .collect(Collectors.partitioningBy((f) -> f.getActionUser().getId() == userId));

        List<UserDto> outgoingFriendRequests = requestMap.get(true).stream()
                .map(Friendship::getActionUser)
                .map(userToUserDto::convert)
                .collect(Collectors.toList());

        List<UserDto> incomingFriendRequests = requestMap.get(false).stream()
                .map(Friendship::getActionUser)
                .map(userToUserDto::convert)
                .collect(Collectors.toList());

        Map<String, List<UserDto>> map = new HashMap<>();
        map.put("outgoingFriendRequests", outgoingFriendRequests);
        map.put("incomingFriendRequests", incomingFriendRequests);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
