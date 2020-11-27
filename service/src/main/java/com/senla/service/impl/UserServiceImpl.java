package com.senla.service.impl;

import com.senla.entity.Community;
import com.senla.entity.User;
import com.senla.exception.SQLErrors;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.UserRepository;
import com.senla.service.CommunityService;
import com.senla.service.ProfileService;
import com.senla.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CommunityService communityService;
    private final ProfileService profileService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CommunityService communityService, ProfileService profileService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.communityService = communityService;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(Long id) {
        log.info("Getting user by id");
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("User with id = %s is not found", id)));
    }

    @Override
    public User findUserByUserName(String userName) {
        log.info("Finding user by user name");
        return userRepository.findByUserName(userName);
    }

    @Override
    public User changeUserPassword(String newPassword, long userId) {
        log.info("Changing user password");
         User user = getUser(userId);
         String codePassword = passwordEncoder.encode(newPassword);
         user.setPassword(codePassword);
         try {
             updateUser(user);
         }
         catch (SQLErrors e){
             log.error("Error when trying to change password");
             throw new SQLErrors("Error when trying to change password");
         }
         return user;
    }

    @Override
    public User addUser(User user) {
        log.info("Adding a new user");
        String codePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(codePassword);
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(long userId) {
        getUser(userId);
        log.info("Deleting user by id");
        User user = getUser(userId);
        List<Community> communities = communityService.getCommunitiesByAdminUserId(userId);

        if(communities.size() != 0){
            for (int i = 0; i < communities.size(); i++) {
                communities.get(i).getUsers().removeAll(communities.get(i).getUsers());
                communities.get(i).getPosts().removeAll(communities.get(i).getPosts());
                communityService.deleteCommunity(communities.get(i).getId());
                profileService.deleteProfile(user.getProfile().getId());
            }

        }
        else {
            userRepository.deleteById(userId);
            profileService.deleteProfile(user.getProfile().getId());
        }
    }

    @Override
    public User findUserByEmail(String email) {
        log.info("Finding user by email");
        return userRepository.findByEmail(email);
    }


    @Override
    public User updateUser(User user) {
        log.info("Updating user");
        String codePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(codePassword);
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Get all users");
        return userRepository.findAll();
    }

    public User getUserById(long userId){
        return getUser(userId);
    }

    public User getUserFromSecurityContext(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        return findUserByUserName(userName);
    }
}
