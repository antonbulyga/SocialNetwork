package com.senla.service.user;

import com.senla.entity.Community;
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

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy CommunityService communityService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.communityService = communityService;
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
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error when trying to change password");
            throw new SQLErrors("Error when trying to change password");
        }
        return user;
    }

    @Override
    public User addUser(User user) {
        log.info("Adding a new user");
        try {
            String codePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(codePassword);
            userRepository.save(user);
        }
        catch (Exception e){
            throw new SQLErrors(e.getMessage());
        }
        return user;
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Deleting user by id");
        User user = getUser(userId);
        List<Community> communities = user.getCommunitiesWhereUserAdmin();
        if (communities.size() != 0) {
            for (Community community : communities) {
                community.getUsers().removeAll(community.getUsers());
                community.getPosts().forEach(post -> post.setUser(null));
                community.setAdminUser(null);
                communityService.deleteCommunity(community.getId());
            }
        }

        user.getPosts().forEach(post -> post.setUser(null));
        user.getMessages().forEach(mess -> mess.setUser(null));
        user.getLikes().forEach(like -> like.setUser(null));
        user.getCommunities().forEach(comm -> comm.setUsers(comm.getUsers().stream().filter(u ->
                !u.getId().equals(userId)).collect(Collectors.toList())));
        user.getDialogs().forEach(dialog -> dialog.setUserList(dialog.getUserList().stream().filter(u ->
                !u.getId().equals(userId)).collect(Collectors.toList())));
        user.getRoles().forEach(role -> role.setUsers(role.getUsers().stream().filter(u ->
                !u.getId().equals(userId)).collect(Collectors.toList())));
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
