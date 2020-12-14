package com.senla.service.friendship;

import com.senla.entity.Friendship;
import com.senla.entity.User;

import java.util.List;

public interface FriendshipService {

    Friendship createFriendRequest(Long userOneId, Long userTwoId, Long actionId);

    Friendship addToFriends(Long userOneId, Long userTwoId, Long actionId);

    List<User> getFriendsListOfUser(Long userId);

    Friendship deleteFriendship(Long userOneId, Long userTwoId, Long actionId);

    Friendship blockUser(Long userOneId, Long userTwoId, Long actionId);

    Friendship unblockUser(Long userOneId, Long userTwoId, Long actionId);

    List<Friendship> getRequestFriendships(Long userId);

    boolean validate(Long userOneId, Long userTwoId, Long actionId);

    List<Friendship> getFriendFriendshipsForUser(Long userId);

    List<Friendship> getBlockedFriendshipsForUser(Long userId);

    List<Friendship> getAllFriendshipsForUser(Long userId);

    Friendship declineFriendRequest(Long userOneId, Long userTwoId, Long actionId);
}
