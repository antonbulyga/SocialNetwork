package com.senla.service.impl;

import com.senla.entity.Friendship;
import com.senla.entity.User;
import com.senla.enumeration.FriendshipStatus;
import com.senla.exception.IncorrectRequest;
import com.senla.repository.FriendshipRepository;
import com.senla.service.FriendshipService;
import com.senla.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserService userService;

    @Autowired
    public FriendshipServiceImpl(FriendshipRepository friendshipRepository,
                                 UserService userService) {
        this.friendshipRepository = friendshipRepository;
        this.userService = userService;
    }

    public boolean validate(Long userOneId, Long userTwoId, Long actionId) {
        if (userOneId > userTwoId & (userOneId == actionId || userTwoId == actionId)) {
            throw new IncorrectRequest("Your request is incorrect. User one id should be less than user two id");
        } else {
            return true;
        }
    }

    @Override
    public Friendship sentNewFriendRequest(Long userOneId, Long userTwoId, Long actionId) {
        User actionUser = userService.getUser(actionId);
        User userOne = userService.getUser(userOneId);
        User userTwo = userService.getUser(userTwoId);
        friendshipRepository.sentNewFriendRequest(userOne, userTwo, actionUser);
        return createFriendship(userOne, userTwo, actionUser, FriendshipStatus.REQUEST);
    }

    @Override
    public Friendship sentFriendRequest(Long userOneId, Long userTwoId, Long actionId) {
        User actionUser = userService.getUser(actionId);
        User userOne = userService.getUser(userOneId);
        User userTwo = userService.getUser(userTwoId);
        friendshipRepository.sentFriendRequest(userOne, userTwo, actionUser);
        return createFriendship(userOne, userTwo, actionUser, FriendshipStatus.REQUEST);
    }

    @Override
    public Friendship addToFriends(Long userOneId, Long userTwoId, Long actionId) {
        User actionUser = userService.getUser(actionId);
        User userOne = userService.getUser(userOneId);
        User userTwo = userService.getUser(userTwoId);
        friendshipRepository.addToFriends(userOne, userTwo, actionUser);
        return createFriendship(userOne, userTwo, actionUser, FriendshipStatus.FRIEND);
    }

    @Override
    public List<User> getFriendsListOfUser(Long userId) {
        User user = userService.getUser(userId);
        List<Friendship> friendships = friendshipRepository.getFriendsListOfUser(user);
        return friendships.stream()
                .map(Friendship::getUserOne)
                .collect(Collectors.toList());
    }


    @Override
    public Friendship deleteFriendship(Long userOneId, Long userTwoId, Long actionId) {
        User actionUser = userService.getUser(actionId);
        User userOne = userService.getUser(userOneId);
        User userTwo = userService.getUser(userTwoId);
        friendshipRepository.deleteFriendship(userOne, userTwo, actionUser);
        return createFriendship(userOne, userTwo, actionUser, FriendshipStatus.DECLINED);
    }

    @Override
    public Friendship blockUser(Long userOneId, Long userTwoId, Long actionId) {
        User actionUser = userService.getUser(actionId);
        User userOne = userService.getUser(userOneId);
        User userTwo = userService.getUser(userTwoId);
        Friendship friendship = createFriendship(userOne, userTwo, actionUser, FriendshipStatus.BLOCKED);
        friendshipRepository.blockUser(userOne, userTwo, actionUser);
        return friendship;
    }


    @Override
    public Friendship unblockUser(Long userOneId, Long userTwoId, Long actionId) {
        User actionUser = userService.getUser(actionId);
        User userOne = userService.getUser(userOneId);
        User userTwo = userService.getUser(userTwoId);
        Friendship friendship = createFriendship(userOne, userTwo, actionUser, FriendshipStatus.DECLINED);
        friendshipRepository.unblockUser(userOne, userTwo, actionUser);
        return friendship;
    }

    @Override
    public List<Friendship> getRequest(Long userId) {
        User user = userService.getUser(userId);
        return friendshipRepository.getRequests(user);
    }

    private Friendship createFriendship(User userOne, User userTwo, User actionUser, FriendshipStatus status) {
        validate(userOne.getId(), userTwo.getId(), actionUser.getId());
        Friendship friendship = new Friendship();
        friendship.setActionUser(actionUser);
        friendship.setUserOne(userOne);
        friendship.setUserTwo(userTwo);
        friendship.setFriendshipStatus(status);
        return friendship;
    }
}
