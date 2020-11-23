package com.senla.model.service;

import com.senla.model.entity.Community;
import com.senla.model.entity.User;

import java.util.List;

public interface CommunityService {
    Community addCommunity(Community community);
    void delete(long id);
    Community getCommunitiesByName(String name);
    Community editCommunity(Community community);
    List<Community> getAll();
    Community getCommunity(Long id);
    List<Community> getCommunitiesByAdminUser_Id(Long adminId);
}

