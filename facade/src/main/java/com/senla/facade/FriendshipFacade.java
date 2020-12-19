package com.senla.facade;

import com.senla.converters.friendship.FriendshipDTOConverter;
import com.senla.converters.user.UserDTOConverter;
import com.senla.dto.friendship.FriendshipDto;
import com.senla.dto.user.UserDto;
import com.senla.dto.user.UserNestedDto;
import com.senla.entity.Friendship;
import com.senla.entity.User;
import com.senla.service.friendship.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FriendshipFacade {

    private final FriendshipService friendshipService;
    private final FriendshipDTOConverter friendshipDTOConverter;
    private final UserDTOConverter userDTOConverter;
    private final UserFacade userFacade;

    @Autowired
    public FriendshipFacade(FriendshipService friendshipService, FriendshipDTOConverter friendshipDTOConverter, UserDTOConverter userDTOConverter, UserFacade userFacade) {
        this.friendshipService = friendshipService;
        this.friendshipDTOConverter = friendshipDTOConverter;
        this.userDTOConverter = userDTOConverter;
        this.userFacade = userFacade;
    }

    public FriendshipDto sentFriendRequest(Long userOneId, Long userTwoId, Long actionUserId) {
        Friendship friendship = friendshipService.createFriendRequest(userOneId, userTwoId, actionUserId);
        return friendshipDTOConverter.convert(friendship);
    }

    public FriendshipDto declineFriendRequest(Long userOneId, Long userTwoId, Long actionUserId) {
        Friendship friendship = friendshipService.declineFriendRequest(userOneId, userTwoId, actionUserId);
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

    public Map<String, List<UserDto>> getOutgoingAndIncomingRequestsForUser(Long userId) {
        List<Friendship> requests = getRequests(userId);
        Map<Boolean, List<Friendship>> requestMap = requests.stream()
                .collect(Collectors.partitioningBy(f -> f.getActionUser().getId().equals(userId)));

        List<UserDto> outgoingFriendRequests = requestMap.get(true).stream()
                .map(f -> {
                    if (!f.getUserOne().getId().equals(userId)) {
                        return f.getUserOne();
                    }
                    return f.getUserTwo();
                })
                .map(userFacade::convertUserToUserDto)
                .collect(Collectors.toList());

        List<UserDto> incomingFriendRequests = requestMap.get(false).stream()
                .map(Friendship::getActionUser)
                .map(userFacade::convertUserToUserDto)
                .collect(Collectors.toList());

        Map<String, List<UserDto>> map = new HashMap<>();
        map.put("outgoingFriendRequests", outgoingFriendRequests);
        map.put("incomingFriendRequests", incomingFriendRequests);
        return map;
    }

    public Set<UserNestedDto> getFriendFriendshipsForUser(Long userId) {
        List<Friendship> friendshipList = friendshipService.getFriendFriendshipsForUser(userId);
        return friendshipList.stream()
                .map(f -> {
                    if (!f.getUserOne().getId().equals(userId)) {
                        return f.getUserOne();
                    }
                    return f.getUserTwo();
                })
                .map(userFacade::convertToUserNestedDto)
                .collect(Collectors.toSet());
    }
}
