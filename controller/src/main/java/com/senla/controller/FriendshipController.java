package com.senla.controller;

import com.senla.dto.friendship.FriendshipDto;
import com.senla.dto.user.UserDto;
import com.senla.dto.user.UserNestedDto;
import com.senla.entity.Friendship;
import com.senla.entity.User;
import com.senla.exception.RestError;
import com.senla.facade.FriendshipFacade;
import com.senla.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * * @author  Anton Bulyha
 * * @version 1.0
 * * @since   2020-08-12
 */
@RestController
@RequestMapping(value = "/friendship")
@Slf4j
public class FriendshipController {

    private final FriendshipFacade friendshipFacade;
    private final UserFacade userFacade;

    @Autowired
    public FriendshipController(FriendshipFacade friendshipFacade, UserFacade userFacade) {
        this.friendshipFacade = friendshipFacade;
        this.userFacade = userFacade;
    }

    /**
     * Sent friend request
     *
     * @param userOneId    first user id
     * @param userTwoId    second user id
     * @param actionUserId action user id
     * @return friendship dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/request")
    public ResponseEntity<FriendshipDto> sentFriendRequest(@RequestParam(name = "idOne") Long userOneId,
                                                           @RequestParam(name = "idTwo") Long userTwoId,
                                                           @RequestParam(name = "idAction") Long actionUserId) {
        User user = userFacade.getUserFromSecurityContext();
        if (user.getId().equals(actionUserId)) {
            FriendshipDto friendshipDto = friendshipFacade.sentFriendRequest(userOneId, userTwoId, actionUserId);
            log.info("Sending a friend request");
            return ResponseEntity.ok()
                    .body(friendshipDto);
        } else {
            log.error("You are trying to send a friend request from another user");
            throw new RestError("You are trying to send a friend request from another user");
        }
    }

    /**
     * Accept friend request
     *
     * @param userOneId    first user id
     * @param userTwoId    second user id
     * @param actionUserId action user id
     * @return friendship dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/accept")
    public ResponseEntity<FriendshipDto> acceptFriendRequest(@RequestParam(name = "idOne") Long userOneId,
                                                             @RequestParam(name = "idTwo") Long userTwoId,
                                                             @RequestParam(name = "idAction") Long actionUserId) {
        User user = userFacade.getUserFromSecurityContext();
        if (user.getId().equals(actionUserId)) {
            FriendshipDto friendshipDto = friendshipFacade.addToFriends(userOneId, userTwoId, actionUserId);
            log.info("Accept friend request");
            return ResponseEntity.ok()
                    .body(friendshipDto);
        } else {
            log.error("You are trying to add to the friends list from another user");
            throw new RestError("You are trying to add to the friends list from another user");
        }
    }

    /**
     * Decline friend request
     *
     * @param userOneId    first user id
     * @param userTwoId    second user id
     * @param actionUserId action user id
     * @return friendship dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/decline")
    public ResponseEntity<FriendshipDto> declineFriendRequest(@RequestParam(name = "idOne") Long userOneId,
                                                              @RequestParam(name = "idTwo") Long userTwoId,
                                                              @RequestParam(name = "idAction") Long actionUserId) {
        User user = userFacade.getUserFromSecurityContext();
        if (user.getId().equals(actionUserId)) {
            FriendshipDto friendshipDto = friendshipFacade.addToFriends(userOneId, userTwoId, actionUserId);
            log.info("Decline friend request");
            return ResponseEntity.ok()
                    .body(friendshipDto);
        } else {
            log.error("You are trying to add to the friends list from another user");
            throw new RestError("You are trying to add to the friends list from another user");
        }
    }

    /**
     * Get friends list of user
     *
     * @param userId user id
     * @return list of the friendship dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/friends")
    public ResponseEntity<List<UserDto>> getFriendsListOfUser(@RequestParam(name = "id") Long userId) {
        User user = userFacade.getUserFromSecurityContext();
        if (user.getId().equals(userId)) {
            List<UserDto> userDto = friendshipFacade.getFriendsListOfUser(userId);
            log.info("Getting a friend list of the user");
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            log.error("You are trying to get a friends list of another user");
            throw new RestError("You are trying to get a friends list of another user");
        }
    }

    /**
     * Delete friendship
     *
     * @param userOneId    first user id
     * @param userTwoId    second user id
     * @param actionUserId action user id
     * @return friendship dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/delete")
    public ResponseEntity<FriendshipDto> deleteFriendship(@RequestParam(name = "idOne") Long userOneId,
                                                          @RequestParam(name = "idTwo") Long userTwoId,
                                                          @RequestParam(name = "idAction") Long actionUserId) {
        User user = userFacade.getUserFromSecurityContext();
        if (user.getId().equals(actionUserId)) {
            FriendshipDto friendshipDto = friendshipFacade.deleteFriendship(userOneId, userTwoId, actionUserId);
            log.info("Deleting the user from the friends");
            return ResponseEntity.ok()
                    .body(friendshipDto);
        } else {
            log.error("You are trying to delete a friendship of another user");
            throw new RestError("You are trying to delete a friendship of another user");
        }
    }

    /**
     * Block user
     *
     * @param userOneId    first user id
     * @param userTwoId    second user id
     * @param actionUserId action user id
     * @return friendship dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/block")
    public ResponseEntity<FriendshipDto> blockUser(@RequestParam(name = "idOne") Long userOneId,
                                                   @RequestParam(name = "idTwo") Long userTwoId,
                                                   @RequestParam(name = "idAction") Long actionUserId) {
        User user = userFacade.getUserFromSecurityContext();
        if (user.getId().equals(actionUserId)) {
            FriendshipDto friendshipDto = friendshipFacade.blockUser(userOneId, userTwoId, actionUserId);
            log.info("Blocking user");
            return ResponseEntity.ok()
                    .body(friendshipDto);
        } else {
            log.error("You are trying to block user from another user");
            throw new RestError("You are trying to block user from another user");
        }
    }

    /**
     * Unblock user
     *
     * @param userOneId    first user id
     * @param userTwoId    second user id
     * @param actionUserId action user id
     * @return friendship dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/unblock")
    public ResponseEntity<FriendshipDto> unblockUser(@RequestParam(name = "idOne") Long userOneId,
                                                     @RequestParam(name = "idTwo") Long userTwoId,
                                                     @RequestParam(name = "idAction") Long actionUserId) {
        User user = userFacade.getUserFromSecurityContext();
        if (user.getId().equals(actionUserId)) {
            FriendshipDto friendshipDto = friendshipFacade.unblockUser(userOneId, userTwoId, actionUserId);
            log.info("Unlocking user");
            return ResponseEntity.ok()
                    .body(friendshipDto);
        } else {
            log.error("You are trying to unblock user from another user");
            throw new RestError("You are trying to unblock user from another user");
        }
    }

    /**
     * Get all requests by user. Show outgoing and incoming friends requests
     *
     * @param userId user id
     * @return map string and list of the user dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/requests")
    public ResponseEntity<Map<String, List<UserDto>>> getRequests(@RequestParam(name = "id") Long userId) {
        User user = userFacade.getUserFromSecurityContext();
        if (user.getId().equals(userId)) {
            List<Friendship> requests = friendshipFacade.getRequests(userId);
            Map<Boolean, List<Friendship>> requestMap = requests.stream()
                    .collect(Collectors.partitioningBy((f) -> f.getActionUser().getId().equals(userId)));

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
        } else {
            log.error("You are trying to get the requests list from another user");
            throw new RestError("You are trying to get the requests list from another user");
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/user/frindslist")
    public ResponseEntity<List<UserNestedDto>> getFriendFriendshipsForUser(@RequestParam(name = "userId") Long userId) {
        User user = userFacade.getUserFromSecurityContext();
        if (user.getId().equals(userId)) {
            List<UserNestedDto> friendsListOfUser;
            List<Friendship> friendshipList = friendshipFacade.getFriendFriendshipsForUser(userId);
            log.info("Getting friends list of user");
            friendsListOfUser = friendshipList.stream()
                    .map(f -> {
                        if (user.getId().equals(userId)) {
                            return f.getUserOne();
                        }
                        return f.getUserTwo();
                    })
                    .map(userFacade::convertToUserNestedDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok()
                    .body(friendsListOfUser);
        } else {
            log.error("You can only view the friends of a registered user");
            throw new RestError("You can only view the friends of a registered user");
        }

    }

}
