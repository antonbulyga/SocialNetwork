package com.senla.service;

import com.senla.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    void deleteUser(long id);
    User findUserByEmail(String email);
    User editUser(User user);
    List<User> getAllUsers();
    User getUserById(long userId);
    User getUser(Long id);
    User findUserByUserName(String userName);
    User changeUserPassword(String newPassword, long userId);
    User getUserFromSecurityContext();
}
