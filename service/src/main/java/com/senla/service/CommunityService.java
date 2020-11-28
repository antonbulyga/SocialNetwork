package com.senla.service;

import com.senla.entity.Community;
import com.senla.entity.User;

import java.util.List;

public interface CommunityService {
    Community addCommunity(Community community);

    void deleteCommunity(long id);

    Community getCommunitiesByName(String name);

    Community updateCommunity(Community community);

    List<Community> getAllCommunities();

    Community getCommunity(Long id);

    List<Community> getCommunitiesByAdminUserId(Long adminId);
}

