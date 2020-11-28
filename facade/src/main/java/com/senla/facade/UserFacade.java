package com.senla.facade;

import com.senla.converters.UserDtoToUser;
import com.senla.converters.UserToUserDto;
import com.senla.dto.UserDto;
import com.senla.entity.User;
import com.senla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserFacade {

    private final UserService userService;
    private final UserToUserDto userToUserDto;
    private final UserDtoToUser userDtoToUser;

    @Autowired
    public UserFacade(UserService userService, UserToUserDto userToUserDto, UserDtoToUser userDtoToUser) {
        this.userService = userService;
        this.userToUserDto = userToUserDto;
        this.userDtoToUser = userDtoToUser;
    }

    public UserDto getUser(Long id) {
        return userToUserDto.convert(userService.getUser(id));
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream().map(userToUserDto::convert).collect(Collectors.toList());
    }

    public UserDto addUser(UserDto userDto) {
        userService.addUser(userDtoToUser.convert(userDto));
        return userDto;
    }

    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    public UserDto findUserByEmail(String email) {
        return userToUserDto.convert(userService.findUserByEmail(email));
    }

    public UserDto updateUser(UserDto userDto) {
        User user = userDtoToUser.convert(userDto);
        userService.updateUser(user);
        return userDto;
    }

    public UserDto findUserByUserName(String userName) {
        return userToUserDto.convert(userService.findUserByUserName(userName));
    }

    public UserDto changeUserPassword(String newPassword, long userId) {
        return userToUserDto.convert(userService.changeUserPassword(newPassword, userId));
    }

    public User getUserFromSecurityContext() {
        return userService.getUserFromSecurityContext();
    }

    public UserDto getUserDtoFromSecurityContext() {
        return userToUserDto.convert(userService.getUserFromSecurityContext());
    }

    public UserDto convertUserToUserDto(User user) {
        return userToUserDto.convert(user);
    }
}
