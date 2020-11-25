package com.senla.service;

import com.senla.entity.Friendship;
import com.senla.entity.User;

import java.util.List;

public interface FriendshipService {
    Friendship sentNewFriendRequest(Long userOneId, Long userTwoId, Long actionId);
    Friendship sentFriendRequest(Long userOneId, Long userTwoId, Long actionId);
    Friendship addToFriends(Long userOneId, Long userTwoId, Long actionId);
    List<User> getFriendsListOfUser(Long userId);
    Friendship deleteFriendship(Long userOneId, Long userTwoId, Long actionId);
    Friendship blockUser(Long userOneId, Long userTwoId, Long actionId);
    Friendship unblockUser(Long userOneId, Long userTwoId, Long actionId);
    List<Friendship> getRequest(Long userId);
}
