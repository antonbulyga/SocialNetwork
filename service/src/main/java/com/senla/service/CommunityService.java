package com.senla.service;

import com.senla.entity.Community;
import com.senla.entity.User;

import java.util.List;

public interface CommunityService {
    Community addCommunity(Community community);
    void delete(long id);
    Community getCommunitiesByName(String name);
    Community editCommunity(Community community);
    List<Community> getAll();
    Community getCommunity(Long id);
    List<Community> getCommunitiesByAdminUserId(Long adminId);
}

