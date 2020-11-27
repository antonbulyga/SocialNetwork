package com.senla.facade;

import com.senla.converters.FriendshipToFriendshipDto;
import com.senla.converters.UserToUserDto;
import com.senla.dto.FriendshipDto;
import com.senla.dto.UserDto;
import com.senla.entity.Friendship;
import com.senla.entity.User;
import com.senla.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendshipFacade {

    private final FriendshipService friendshipService;
    private final FriendshipToFriendshipDto friendshipToFriendshipDto;
    private final UserToUserDto userToUserDto;

    @Autowired
    public FriendshipFacade(FriendshipService friendshipService, FriendshipToFriendshipDto friendshipToFriendshipDto, UserToUserDto userToUserDto) {
        this.friendshipService = friendshipService;
        this.friendshipToFriendshipDto = friendshipToFriendshipDto;
        this.userToUserDto = userToUserDto;
    }

    public FriendshipDto sentNewFriendRequest(Long userOneId, Long userTwoId, Long actionUserId){
       Friendship friendship = friendshipService.sentNewFriendRequest(userOneId, userTwoId, actionUserId);
        return friendshipToFriendshipDto.convert(friendship);
    }

    public FriendshipDto sentFriendRequest(Long userOneId,Long userTwoId,Long actionUserId){
        Friendship friendship = friendshipService.sentFriendRequest(userOneId, userTwoId, actionUserId);
        return friendshipToFriendshipDto.convert(friendship);
    }

    public FriendshipDto addToFriends(Long userOneId,Long userTwoId,Long actionUserId){
        Friendship friendship = friendshipService.addToFriends(userOneId, userTwoId, actionUserId);
        return friendshipToFriendshipDto.convert(friendship);
    }

    public FriendshipDto deleteFriendship(Long userOneId,Long userTwoId,Long actionUserId){
        Friendship friendship = friendshipService.deleteFriendship(userOneId, userTwoId, actionUserId);
        return friendshipToFriendshipDto.convert(friendship);
    }

    public FriendshipDto blockUser(Long userOneId, Long userTwoId, Long actionUserId){
        Friendship friendship = friendshipService.blockUser(userOneId, userTwoId, actionUserId);
        return friendshipToFriendshipDto.convert(friendship);
    }

    public FriendshipDto unblockUser(Long userOneId,Long userTwoId,Long actionUserId){
        Friendship friendship = friendshipService.unblockUser(userOneId, userTwoId, actionUserId);
        return friendshipToFriendshipDto.convert(friendship);
    }

    public List<Friendship> getRequests(Long userId){
       return friendshipService.getRequest(userId);
    }

    public List<UserDto> getFriendsListOfUser(Long userId){
        List<User> users = friendshipService.getFriendsListOfUser(userId);
        return users.stream().map(userToUserDto::convert).collect(Collectors.toList());
    }

}
