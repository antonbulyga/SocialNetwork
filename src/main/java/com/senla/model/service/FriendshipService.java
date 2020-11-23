package com.senla.model.service;

import com.senla.model.dto.UserDto;
import com.senla.model.entity.Dialog;
import com.senla.model.entity.Friendship;
import com.senla.model.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FriendshipService {
    Friendship sentNewFriendRequest(Long userOneId, Long userTwoId, Long actionId);
    Friendship sentFriendRequest(Long userOneId, Long userTwoId, Long actionId);
    Friendship addToFriends(Long userOneId, Long userTwoId, Long actionId);
    List<User> getFriendsListOfUser(Long userId);
    Friendship deleteFriendship(Long userOneId, Long userTwoId, Long actionId);
    Friendship blockUser(Long userOneId, Long userTwoId, Long actionId);
    Friendship unblockUser(Long userOneId, Long userTwoId, Long actionId);
    Map<String, List<UserDto>> getRequest(Long userId);
}
