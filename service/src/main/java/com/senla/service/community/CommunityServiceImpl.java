package com.senla.service.community;

import com.senla.entity.Community;
import com.senla.entity.User;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.CommunityRepository;
import com.senla.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final UserService userService;

    @Autowired
    public CommunityServiceImpl(CommunityRepository communityRepository, @Lazy UserService userService) {
        this.communityRepository = communityRepository;
        this.userService = userService;
    }

    @Override
    public Community addCommunity(Community community) {
        log.info("Adding community");
        communityRepository.save(community);
        return community;
    }

    @Override
    public void deleteCommunity(long id) {
        getCommunity(id);
        log.info("Deleting community by id");
        communityRepository.deleteById(id);
    }

    @Override
    public Community getCommunityByName(String name) {
        log.info("Getting community by name");
        return communityRepository.getCommunityByName(name);
    }

    @Override
    public Community updateCommunity(Community community) {
        log.info("Updating community");
        communityRepository.save(community);
        return community;
    }

    @Override
    public List<Community> getAllCommunities() {
        log.info("Getting all communities");
        return communityRepository.findAll();
    }

    @Override
    public Community getCommunity(Long id) {
        log.info("Getting community by id");
        return communityRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Community with id = %s is not found", id)));
    }

    @Override
    public List<Community> getCommunitiesByAdminUserId(Long adminId) {
        log.info("Getting community by admin user");
        return communityRepository.getCommunitiesByAdminUser_Id(adminId);
    }

    @Override
    public Optional<Community> getCommunityById(Long id) {
        return communityRepository.findById(id);
    }

    @Override
    public Community addUserToCommunity(Long communityId, Long userId) {
        Community community = getCommunity(communityId);
        User user = userService.getUser(userId);
        List<User> users = community.getUsers();
        users.add(user);
        log.info("Adding user to the community");
        return updateCommunity(community);
    }

    @Override
    public void removeUserFromCommunity(Long communityId, Long userId) {
        Community community = getCommunity(communityId);
        User user = userService.getUser(userId);
        List<User> users = community.getUsers();
        users.remove(user);
        log.info("Remove user from the community");
        updateCommunity(community);
    }

}
