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

    private UserRepository userRepository;
    private CommunityService communityService;
    private ProfileService profileService;
    private BCryptPasswordEncoder passwordEncoder;

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
        User user = userRepository.findByUserName(userName);
        return user;
    }

    @Override
    public User changeUserPassword(String newPassword, long userId) {
        log.info("Changing user password");
         User user = getUser(userId);
         String codePassword = passwordEncoder.encode(newPassword);
         user.setPassword(codePassword);
         try {
             editUser(user);
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
        log.info("Deleting user by id");
        User user = getUser(userId);
        List<Community> communities = communityService.getCommunitiesByAdminUserId(userId);

        if(communities.size() != 0){
            for (int i = 0; i < communities.size(); i++) {
                communities.get(i).getUsers().removeAll(communities.get(i).getUsers());
                communities.get(i).getPosts().removeAll(communities.get(i).getPosts());
                communityService.delete(communities.get(i).getId());
                profileService.deleteProfiles(user.getProfile().getId());
            }

        }
        else {
            userRepository.deleteById(userId);
            profileService.deleteProfiles(user.getProfile().getId());
        }
    }

    @Override
    public User findUserByEmail(String email) {
        log.info("Finding user by email");
        User user = userRepository.findByEmail(email);
        return user;
    }


    @Override
    public User editUser(User user) {
        log.info("Updating user");
        String codePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(codePassword);
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Get all users");
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public User getUserById(long userId){
       User user = getUser(userId);
       return user;
    }

    public User getUserFromSecurityContext(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        User user = findUserByUserName(userName);
        return user;
    }
}
