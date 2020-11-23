package com.senla.model.service.impl;

import com.senla.model.converters.UserToUserDto;
import com.senla.model.dto.FriendshipDto;
import com.senla.model.dto.UserDto;
import com.senla.model.entity.Friendship;
import com.senla.model.entity.User;
import com.senla.model.enumeration.FriendshipStatus;
import com.senla.model.exception.IncorrectRequest;
import com.senla.model.repository.FriendshipRepository;
import com.senla.model.service.FriendshipService;
import com.senla.model.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class FriendshipServiceImpl implements FriendshipService {
    private FriendshipRepository friendshipRepository;
    private UserService userService;
    private UserToUserDto userToUserDto;

    @Autowired
    public FriendshipServiceImpl(FriendshipRepository friendshipRepository,
                                 UserService userService, UserToUserDto userToUserDto) {
        this.friendshipRepository = friendshipRepository;
        this.userService = userService;
        this.userToUserDto = userToUserDto;
    }

    public Map<String, List<UserDto>> getRequest(Long userId) {
        User user = userService.getUser(userId);
        List<Friendship> requests = friendshipRepository.getRequests(user);
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
        return map;
    }


    @Override
    public Friendship sentNewFriendRequest(Long userOneId, Long userTwoId, Long actionId) {
        Friendship friendship = new Friendship();
        if (userOneId > userTwoId & (userOneId == actionId || userTwoId == actionId)) {
            throw new IncorrectRequest("Your request is incorrect. User one id should be less than user two id");
        } else {
            User actionUser = userService.getUser(actionId);
            User userOne = userService.getUser(userOneId);
            User userTwo = userService.getUser(userTwoId);
            friendshipRepository.sentNewFriendRequest(userOne, userTwo, actionUser);
            friendship.setUserOne(userOne);
            friendship.setUserTwo(userTwo);
            friendship.setActionUser(actionUser);
            friendship.setFriendshipStatus(FriendshipStatus.REQUEST);
        }
        return friendship;
    }

    @Override
    public Friendship sentFriendRequest(Long userOneId, Long userTwoId, Long actionId) {
        Friendship friendship = new Friendship();
        if (userOneId > userTwoId & (userOneId == actionId || userTwoId == actionId)) {
            throw new IncorrectRequest("Your request is incorrect. User one id should be less than user two id");
        } else {
            User actionUser = userService.getUser(actionId);
            User userOne = userService.getUser(userOneId);
            User userTwo = userService.getUser(userTwoId);
            friendshipRepository.sentFriendRequest(userOne, userTwo, actionUser);
            friendshipRepository.sentNewFriendRequest(userOne, userTwo, actionUser);
            friendship.setUserOne(userOne);
            friendship.setUserTwo(userTwo);
            friendship.setActionUser(actionUser);
            friendship.setFriendshipStatus(FriendshipStatus.REQUEST);
        }
        return friendship;
    }

    @Override
    public Friendship addToFriends(Long userOneId, Long userTwoId, Long actionId) {
        Friendship friendship = new Friendship();
        if (userOneId > userTwoId & (userOneId == actionId || userTwoId == actionId)) {
            throw new IncorrectRequest("Your request is incorrect. User one id should be less than user two id");
        } else {
            User actionUser = userService.getUser(actionId);
            User userOne = userService.getUser(userOneId);
            User userTwo = userService.getUser(userTwoId);
            friendshipRepository.addToFriends(userOne, userTwo, actionUser);
            friendship.setUserOne(userOne);
            friendship.setUserTwo(userTwo);
            friendship.setActionUser(actionUser);
            friendship.setFriendshipStatus(FriendshipStatus.FRIEND);
        }
        return friendship;
    }

    @Override
    public List<User> getFriendsListOfUser(Long userId) {
        User user = userService.getUser(userId);
        List<Friendship> friendships = friendshipRepository.getFriendsListOfUser(user);
        List<User> users = friendships.stream()
                .map(Friendship::getUserOne)
                .collect(Collectors.toList());
        return users;
    }


    @Override
    public Friendship deleteFriendship(Long userOneId, Long userTwoId, Long actionId) {
        Friendship friendship = new Friendship();
        if (userOneId > userTwoId & (userOneId == actionId || userTwoId == actionId)) {
            throw new IncorrectRequest("Your request is incorrect. User one id should be less than user two id or" +
                    " neither the first nor second user is an action user");
        } else {
            User actionUser = userService.getUser(actionId);
            User userOne = userService.getUser(userOneId);
            User userTwo = userService.getUser(userTwoId);
            friendshipRepository.deleteFriendship(userOne, userTwo, actionUser);
            friendship.setUserOne(userOne);
            friendship.setUserTwo(userTwo);
            friendship.setActionUser(actionUser);
            friendship.setFriendshipStatus(FriendshipStatus.DECLINED);
        }
        return friendship;
    }

    @Override
    public Friendship blockUser(Long userOneId, Long userTwoId, Long actionId) {
        Friendship friendship = new Friendship();
        if (userOneId > userTwoId & (userOneId == actionId || userTwoId == actionId)) {
            throw new IncorrectRequest("Your request is incorrect. User one id should be less than user two id or" +
                    " neither the first nor second user is an action user");
        } else {
            User actionUser = userService.getUser(actionId);
            friendship.setActionUser(actionUser);
            User userOne = userService.getUser(userOneId);
            friendship.setUserOne(userOne);
            User userTwo = userService.getUser(userTwoId);
            friendship.setUserTwo(userTwo);
            friendship.setFriendshipStatus(FriendshipStatus.BLOCKED);
            friendshipRepository.blockUser(userOne, userTwo, actionUser);

        }
        return friendship;
    }

    @Override
    public Friendship unblockUser(Long userOneId, Long userTwoId, Long actionId) {
        Friendship friendship = new Friendship();
        if (userOneId > userTwoId & (userOneId == actionId || userTwoId == actionId)) {
            throw new IncorrectRequest("Your request is incorrect. User one id should be less than user two id or" +
                    " neither the first nor second user is an action user");
        } else {
            User actionUser = userService.getUser(actionId);
            friendship.setActionUser(actionUser);
            User userOne = userService.getUser(userOneId);
            friendship.setUserOne(userOne);
            User userTwo = userService.getUser(userTwoId);
            friendship.setUserTwo(userTwo);
            friendship.setFriendshipStatus(FriendshipStatus.DECLINED);
            friendshipRepository.unblockUser(userOne, userTwo, actionUser);
        }
        return friendship;
    }
}
