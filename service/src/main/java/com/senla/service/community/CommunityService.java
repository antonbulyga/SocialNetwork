package com.senla.service.community;

import com.senla.entity.Community;

import java.util.List;
import java.util.Optional;

public interface CommunityService {
    Community addCommunity(Community community);

    void deleteCommunity(long id);

    List<Community> getCommunityByName(String name);

    Community updateCommunity(Community community);

    List<Community> getAllCommunities();

    Community getCommunity(Long id);

    List<Community> getCommunitiesByAdminUserId(Long adminId);

    Optional<Community> getCommunityById(Long id);

    Community addUserToCommunity(Long communityId, Long userId);

    void removeUserFromCommunity(Long communityId, Long userId);

}

