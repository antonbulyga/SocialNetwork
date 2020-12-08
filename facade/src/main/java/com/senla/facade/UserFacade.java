package com.senla.facade;

import com.senla.converters.user.ReverseUserDTOConverter;
import com.senla.converters.user.UserDTOConverter;
import com.senla.dto.user.UserDto;
import com.senla.entity.User;
import com.senla.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserFacade {

    private final UserService userService;
    private final UserDTOConverter userDTOConverter;
    private final ReverseUserDTOConverter reverseUserDTOConverter;

    @Autowired
    public UserFacade(UserService userService, UserDTOConverter userDTOConverter, ReverseUserDTOConverter reverseUserDTOConverter) {
        this.userService = userService;
        this.userDTOConverter = userDTOConverter;
        this.reverseUserDTOConverter = reverseUserDTOConverter;
    }

    public UserDto getUser(Long id) {
        return userDTOConverter.convert(userService.getUser(id));
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream().map(userDTOConverter::convert).collect(Collectors.toList());
    }

    public UserDto addUser(UserDto userDto) {
        User user =  userService.addUser(reverseUserDTOConverter.convert(userDto));
        UserDto userDtoWithDate = convertUserToUserDto(user);
        return userDtoWithDate;
    }

    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    public UserDto findUserByEmail(String email) {
        return userDTOConverter.convert(userService.findUserByEmail(email));
    }

    public UserDto updateUser(UserDto userDto) {
        User user =  userService.updateUser(reverseUserDTOConverter.convert(userDto));
        UserDto userDtoWithDate = convertUserToUserDto(user);
        return userDtoWithDate;
    }

    public UserDto findUserByUserName(String userName) {
        return userDTOConverter.convert(userService.findUserByUserName(userName));
    }

    public UserDto changeUserPassword(String newPassword, long userId) {
        return userDTOConverter.convert(userService.changeUserPassword(newPassword, userId));
    }

    public User getUserFromSecurityContext() {
        return userService.getUserFromSecurityContext();
    }

    public UserDto getUserDtoFromSecurityContext() {
        return userDTOConverter.convert(userService.getUserFromSecurityContext());
    }

    public UserDto convertUserToUserDto(User user) {
        return userDTOConverter.convert(user);
    }
}
