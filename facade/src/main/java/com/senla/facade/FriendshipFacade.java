package com.senla.facade;

import com.senla.converters.friendship.FriendshipDTOConverter;
import com.senla.converters.user.UserDTOConverter;
import com.senla.dto.friendship.FriendshipDto;
import com.senla.dto.user.UserDto;
import com.senla.entity.Friendship;
import com.senla.entity.User;
import com.senla.service.friendship.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendshipFacade {

    private final FriendshipService friendshipService;
    private final FriendshipDTOConverter friendshipDTOConverter;
    private final UserDTOConverter userDTOConverter;

    @Autowired
    public FriendshipFacade(FriendshipService friendshipService, FriendshipDTOConverter friendshipDTOConverter, UserDTOConverter userDTOConverter) {
        this.friendshipService = friendshipService;
        this.friendshipDTOConverter = friendshipDTOConverter;
        this.userDTOConverter = userDTOConverter;
    }

    public FriendshipDto sentNewFriendRequest(Long userOneId, Long userTwoId, Long actionUserId) {
        Friendship friendship = friendshipService.createFriendRequest(userOneId, userTwoId, actionUserId);
        return friendshipDTOConverter.convert(friendship);
    }

    public FriendshipDto sentFriendRequest(Long userOneId, Long userTwoId, Long actionUserId) {
        Friendship friendship = friendshipService.createFriendRequest(userOneId, userTwoId, actionUserId);
        return friendshipDTOConverter.convert(friendship);
    }

    public FriendshipDto addToFriends(Long userOneId, Long userTwoId, Long actionUserId) {
        Friendship friendship = friendshipService.addToFriends(userOneId, userTwoId, actionUserId);
        return friendshipDTOConverter.convert(friendship);
    }

    public FriendshipDto deleteFriendship(Long userOneId, Long userTwoId, Long actionUserId) {
        Friendship friendship = friendshipService.deleteFriendship(userOneId, userTwoId, actionUserId);
        return friendshipDTOConverter.convert(friendship);
    }

    public FriendshipDto blockUser(Long userOneId, Long userTwoId, Long actionUserId) {
        Friendship friendship = friendshipService.blockUser(userOneId, userTwoId, actionUserId);
        return friendshipDTOConverter.convert(friendship);
    }

    public FriendshipDto unblockUser(Long userOneId, Long userTwoId, Long actionUserId) {
        Friendship friendship = friendshipService.unblockUser(userOneId, userTwoId, actionUserId);
        return friendshipDTOConverter.convert(friendship);
    }

    public List<Friendship> getRequests(Long userId) {
        return friendshipService.getRequestFriendships(userId);
    }

    public List<UserDto> getFriendsListOfUser(Long userId) {
        List<User> users = friendshipService.getFriendsListOfUser(userId);
        return users.stream().map(userDTOConverter::convert).collect(Collectors.toList());
    }

}
