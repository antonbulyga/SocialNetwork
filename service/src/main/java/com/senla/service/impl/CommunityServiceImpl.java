package com.senla.service.impl;

import com.senla.entity.Community;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.CommunityRepository;
import com.senla.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    @Autowired
    public CommunityServiceImpl(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Override
    public Community addCommunity(Community community) {
        communityRepository.save(community);
        return community;
    }

    @Override
    public void deleteCommunity(long id) {
        getCommunity(id);
        communityRepository.deleteById(id);
    }

    @Override
    public Community getCommunitiesByName(String name) {
        return communityRepository.getCommunitiesByName(name);
    }

    @Override
    public Community updateCommunity(Community community) {
        communityRepository.save(community);
        return community;
    }

    @Override
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    @Override
    public Community getCommunity(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Community with id = %s is not found", id)));
    }

    @Override
    public List<Community> getCommunitiesByAdminUserId(Long adminId) {
        return communityRepository.getCommunitiesByAdminUser_Id(adminId);
    }


}
