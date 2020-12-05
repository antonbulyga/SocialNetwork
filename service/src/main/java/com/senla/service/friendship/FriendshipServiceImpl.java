package com.senla.service.friendship;

import com.senla.entity.Friendship;
import com.senla.entity.User;
import com.senla.enumeration.FriendshipStatus;
import com.senla.exception.IncorrectRequest;
import com.senla.repository.FriendshipRepository;
import com.senla.service.user.UserService;
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
        if (userOneId < userTwoId) {
            if ((userOneId.equals(actionId) || userTwoId.equals(actionId))) {
                log.info("Parameters is correct");
                return true;
            } else {
                log.error("Your request is incorrect action id should be one of the users");
                throw new IncorrectRequest("Your request is incorrect action id should be one of the users");
            }

        } else {
            log.error("Your request is incorrect. User one id should be less than user two id");
            throw new IncorrectRequest("Your request is incorrect. User one id should be less than user two id");
        }

    }


    @Override
    public Friendship createFriendRequest(Long userOneId, Long userTwoId, Long actionId) {
        validate(userOneId, userTwoId, actionId);
        User actionUser = userService.getUser(actionId);
        User userOne = userService.getUser(userOneId);
        User userTwo = userService.getUser(userTwoId);
        List<Friendship> friendships = friendshipRepository.getAllFriendshipsForUserOneAndUserTwo(userOne, userTwo);
        if (friendships.isEmpty()) {
            friendshipRepository.createNewFriendRequest(userOne, userTwo, actionUser);
            return createFriendship(userOne, userTwo, actionUser, FriendshipStatus.REQUEST);
        } else {
            for (Friendship f : friendships) {
                if (f.getActionUser().equals(actionUser) & f.getUserOne().equals(userOne)
                        & f.getUserTwo().equals(userTwo) & f.getFriendshipStatus().equals(FriendshipStatus.DECLINED)) {
                    friendshipRepository.sentFriendRequest(userOne, userTwo, actionUser);
                    return createFriendship(userOne, userTwo, actionUser, FriendshipStatus.REQUEST);
                }
            }
        }
        log.error("Relationship of that users already exists");
        throw new IncorrectRequest("Relationship of that users already exists");
    }

    @Override
    public Friendship addToFriends(Long userOneId, Long userTwoId, Long actionId) {
        validate(userOneId, userTwoId, actionId);
        User userOne = userService.getUser(userOneId);
        User userTwo = userService.getUser(userTwoId);
        List<Friendship> friendships = getRequestFriendships(actionId);
        for (Friendship f : friendships) {
            if (f.getActionUser().equals(userOne) & f.getUserOne().equals(userOne)
                    & f.getUserTwo().equals(userTwo)) {
                friendshipRepository.addToFriends(userOne, userTwo, userTwo);
                return createFriendship(userOne, userTwo, userTwo, FriendshipStatus.FRIEND);
            }
        }
        log.error("Relationship of that users already exists");
        throw new IncorrectRequest("Relationship of that users already exists");
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
        validate(userOneId, userTwoId, actionId);
        User actionUser = userService.getUser(actionId);
        User userOne = userService.getUser(userOneId);
        User userTwo = userService.getUser(userTwoId);
        List<Friendship> friendships = getFriendFriendshipsForUser(actionId);
        for (Friendship f : friendships) {
            if (f.getUserOne().equals(userOne) & f.getUserTwo().equals(userTwo)) {
                friendshipRepository.deleteFriendship(userOne, userTwo, actionUser);
                return createFriendship(userOne, userTwo, actionUser, FriendshipStatus.DECLINED);
            }
        }
        log.error("Relationship of that users already exists");
        throw new IncorrectRequest("Relationship of that users already exists");
    }

    @Override
    public Friendship blockUser(Long userOneId, Long userTwoId, Long actionId) {
        validate(userOneId, userTwoId, actionId);
        User actionUser = userService.getUser(actionId);
        User userOne = userService.getUser(userOneId);
        User userTwo = userService.getUser(userTwoId);
        List<Friendship> friendships = getAllFriendshipsForUser(actionId);
        for (Friendship f : friendships) {
            if (f.getActionUser().equals(actionUser) & f.getUserOne().equals(userOne)
                    & f.getUserTwo().equals(userTwo) & !f.getFriendshipStatus().equals(FriendshipStatus.BLOCKED) ) {
                friendshipRepository.blockUser(userOne, userTwo, actionUser);
                return createFriendship(userOne, userTwo, actionUser, FriendshipStatus.BLOCKED);
            }
        }
        log.error("Relationship of that users already exists");
        throw new IncorrectRequest("Relationship of that users already exists");
    }


    @Override
    public Friendship unblockUser(Long userOneId, Long userTwoId, Long actionId) {
        validate(userOneId, userTwoId, actionId);
        User actionUser = userService.getUser(actionId);
        User userOne = userService.getUser(userOneId);
        User userTwo = userService.getUser(userTwoId);
        List<Friendship> friendships = getBlockedFriendshipsForUser(actionId);
        for (Friendship f : friendships) {
            if (f.getActionUser().equals(actionUser) & f.getUserOne().equals(userOne)
                    & f.getUserTwo().equals(userTwo)) {
                friendshipRepository.unblockUser(userOne, userTwo, actionUser);
                return createFriendship(userOne, userTwo, actionUser, FriendshipStatus.DECLINED);
            }
        }
        log.error("Relationship of that users already exists");
        throw new IncorrectRequest("Relationship of that users already exists");
    }

    @Override
    public List<Friendship> getRequestFriendships(Long userId) {
        User user = userService.getUser(userId);
        List<Friendship> friendships = friendshipRepository.getRequestFriendshipsForUser(user);
        if (friendships.isEmpty()) {
            log.error("No users with that relationship. Incorrect request, try again");
            throw new IncorrectRequest("No users with that relationship. Incorrect request, try again");
        }
        return friendships;
    }

    public List<Friendship> getFriendFriendshipsForUser(Long userId) {
        User user = userService.getUser(userId);
        List<Friendship> friendships = friendshipRepository.getFriendFriendshipsForUser(user);
        if (friendships.isEmpty()) {
            log.error("No users with that relationship. Incorrect request, try again");
            throw new IncorrectRequest("No users with that relationship. Incorrect request, try again");
        }
        return friendships;
    }

    public List<Friendship> getBlockedFriendshipsForUser(Long userId) {
        User user = userService.getUser(userId);
        List<Friendship> friendships = friendshipRepository.getBlockedFriendshipsForUser(user);
        if (friendships.isEmpty()) {
            log.error("No users with that relationship. Incorrect request, try again");
            throw new IncorrectRequest("No users with that relationship. Incorrect request, try again");
        }
        return friendships;
    }

    public List<Friendship> getAllFriendshipsForUser(Long userId) {
        User user = userService.getUser(userId);
        List<Friendship> friendships = friendshipRepository.getAllFriendshipsForUser(user);
        if (friendships.isEmpty()) {
            log.error("No users with that relationship. Incorrect request, try again");
            throw new IncorrectRequest("No users with that relationship. Incorrect request, try again");
        }
        return friendships;
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
