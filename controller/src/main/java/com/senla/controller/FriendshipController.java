package com.senla.controller;

import com.senla.dto.FriendshipDto;
import com.senla.dto.UserDto;
import com.senla.entity.Friendship;
import com.senla.facade.FriendshipFacade;
import com.senla.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FriendshipController {

    private final FriendshipFacade friendshipFacade;
    private final UserFacade userFacade;

    @Autowired
    public FriendshipController(FriendshipFacade friendshipFacade, UserFacade userFacade) {
        this.friendshipFacade = friendshipFacade;
        this.userFacade = userFacade;
    }

    @PostMapping(value = "request/new")
    public ResponseEntity<FriendshipDto> sentNewFriendRequest(@RequestParam(name = "idOne") Long userOneId,
                                                              @RequestParam(name = "idTwo") Long userTwoId,
                                                              @RequestParam(name = "idAction") Long actionUserId) {

        FriendshipDto friendshipDto = friendshipFacade.sentNewFriendRequest(userOneId, userTwoId, actionUserId);
        log.info("Sending a new friend request");
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @PostMapping(value = "request")
    public ResponseEntity<FriendshipDto> sentFriendRequest(@RequestParam(name = "idOne") Long userOneId,
                                                           @RequestParam(name = "idTwo") Long userTwoId,
                                                           @RequestParam(name = "idAction") Long actionUserId) {
        FriendshipDto friendshipDto = friendshipFacade.sentFriendRequest(userOneId, userTwoId, actionUserId);
        log.info("Sending a friend request");
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @PostMapping(value = "add")
    public ResponseEntity<FriendshipDto> addToFriends(@RequestParam(name = "idOne") Long userOneId,
                                                      @RequestParam(name = "idTwo") Long userTwoId,
                                                      @RequestParam(name = "idAction") Long actionUserId) {
        FriendshipDto friendshipDto = friendshipFacade.addToFriends(userOneId, userTwoId, actionUserId);
        log.info("Adding to friend user");
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @GetMapping(value = "friends")
    public ResponseEntity<List<UserDto>> getFriendsListOfUser(@RequestParam(name = "id") Long userId) {
        List<UserDto> userDto = friendshipFacade.getFriendsListOfUser(userId);
        log.info("Getting a friend list of the user");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping(value = "delete")
    public ResponseEntity<FriendshipDto> deleteFriendship(@RequestParam(name = "idOne") Long userOneId,
                                                          @RequestParam(name = "idTwo") Long userTwoId,
                                                          @RequestParam(name = "idAction") Long actionUserId) {
        FriendshipDto friendshipDto = friendshipFacade.deleteFriendship(userOneId, userTwoId, actionUserId);
        log.info("Deleting the user from the friends");
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @PostMapping(value = "block")
    public ResponseEntity<FriendshipDto> blockUser(@RequestParam(name = "idOne") Long userOneId,
                                                   @RequestParam(name = "idTwo") Long userTwoId,
                                                   @RequestParam(name = "idAction") Long actionUserId) {
        FriendshipDto friendshipDto = friendshipFacade.blockUser(userOneId, userTwoId, actionUserId);
        log.info("Blocking user");
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @PostMapping(value = "unblock")
    public ResponseEntity<FriendshipDto> unblockUser(@RequestParam(name = "idOne") Long userOneId,
                                                     @RequestParam(name = "idTwo") Long userTwoId,
                                                     @RequestParam(name = "idAction") Long actionUserId) {

        FriendshipDto friendshipDto = friendshipFacade.unblockUser(userOneId, userTwoId, actionUserId);
        log.info("Unlocking user");
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @GetMapping(value = "requests")
    public ResponseEntity<Map<String, List<UserDto>>> getRequests(@RequestParam(name = "id") Long userId) {
        List<Friendship> requests = friendshipFacade.getRequests(userId);
        Map<Boolean, List<Friendship>> requestMap = requests.stream()
                .collect(Collectors.partitioningBy((f) -> f.getActionUser().getId() == userId));

        List<UserDto> outgoingFriendRequests = requestMap.get(true).stream()
                .map(Friendship::getActionUser)
                .map(userFacade::convertUserToUserDto)
                .collect(Collectors.toList());

        List<UserDto> incomingFriendRequests = requestMap.get(false).stream()
                .map(Friendship::getActionUser)
                .map(userFacade::convertUserToUserDto)
                .collect(Collectors.toList());

        Map<String, List<UserDto>> map = new HashMap<>();
        map.put("outgoingFriendRequests", outgoingFriendRequests);
        map.put("incomingFriendRequests", incomingFriendRequests);
        log.info("Getting user requests");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
