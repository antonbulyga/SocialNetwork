package com.senla.model.service.impl;

import com.senla.model.entity.Community;
import com.senla.model.exception.EntityNotFoundException;
import com.senla.model.repository.CommunityRepository;
import com.senla.model.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CommunityServiceImpl implements CommunityService {

    private CommunityRepository communityRepository;

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
    public void delete(long id) {
         communityRepository.deleteById(id);
    }

    @Override
    public Community getCommunitiesByName(String name) {
       Community community = communityRepository.getCommunitiesByName(name);
        return community;
    }

    @Override
    public Community editCommunity(Community community) {
        communityRepository.save(community);
        return community;
    }

    @Override
    public List<Community> getAll() {
         List<Community> communities = communityRepository.findAll();
        return communities;
    }

    @Override
    public Community getCommunity(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Community with id = %s is not found", id)));
    }

    @Override
    public List<Community> getCommunitiesByAdminUser_Id(Long adminId) {
        List<Community> communities = communityRepository.getCommunitiesByAdminUser_Id(adminId);
        return communities;
    }


}
