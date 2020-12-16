package com.senla.service.user;

import com.senla.entity.Community;
import com.senla.entity.Like;
import com.senla.entity.Profile;
import com.senla.entity.User;
import com.senla.exception.EntityNotFoundException;
import com.senla.exception.SQLErrors;
import com.senla.repository.UserRepository;
import com.senla.service.community.CommunityService;
import com.senla.service.profile.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CommunityService communityService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProfileService profileService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy CommunityService communityService, BCryptPasswordEncoder passwordEncoder, ProfileService profileService) {
        this.userRepository = userRepository;
        this.communityService = communityService;
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
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
        if (user == null) {
            log.warn("No user found with this name");
            throw new EntityNotFoundException("No user found with this name");
        }
        return user;
    }

    @Override
    public User changeUserPassword(String newPassword, Long userId) {
        log.info("Changing user password");
        User user = getUser(userId);
        String codePassword = passwordEncoder.encode(newPassword);
        user.setPassword(codePassword);
        try {
            updateUser(user);
        } catch (SQLErrors e) {
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
    public void deleteUser(Long userId) {
        log.info("Deleting user by id");
        List<Community> communities = communityService.getCommunitiesByAdminUserId(userId);
        if (communities.size() != 0) {
            for (int i = 0; i < communities.size(); i++) {
                communities.get(i).getUsers().removeAll(communities.get(i).getUsers());
                communities.get(i).getPosts().removeAll(communities.get(i).getPosts());
                communityService.deleteCommunity(communities.get(i).getId());
            }
        }
        userRepository.deleteById(userId);
    }

    @Override
    public User findUserByEmail(String email) {
        log.info("Finding user by email");
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("No user found by this email");
        }
        return user;
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
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            log.warn("Empty user list");
            throw new EntityNotFoundException("Empty user list");
        }
        return users;
    }

    public User getUserById(Long userId) {
        return getUser(userId);
    }

    public User getUserFromSecurityContext() {
        log.info("Getting user from security context");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        return findUserByUserName(userName);
    }
}
