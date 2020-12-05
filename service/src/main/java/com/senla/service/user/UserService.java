package com.senla.service.user;

import com.senla.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    void deleteUser(Long id);

    User findUserByEmail(String email);

    User updateUser(User user);

    List<User> getAllUsers();

    User getUserById(long userId);

    User getUser(Long id);

    User findUserByUserName(String userName);

    User changeUserPassword(String newPassword, long userId);

    User getUserFromSecurityContext();
}
