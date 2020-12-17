package com.senla.facade;

import com.senla.converters.user.ReverseUserDTOConverter;
import com.senla.converters.user.UserDTOConverter;
import com.senla.converters.user.UserToUserNestedDtoConverter;
import com.senla.dto.user.UserDto;
import com.senla.dto.user.UserNestedDto;
import com.senla.entity.User;
import com.senla.service.profile.ProfileService;
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
    private final UserToUserNestedDtoConverter userToUserNestedDtoConverter;

    @Autowired
    public UserFacade(UserService userService, UserDTOConverter userDTOConverter, ReverseUserDTOConverter reverseUserDTOConverter, UserToUserNestedDtoConverter userToUserNestedDtoConverter) {
        this.userService = userService;
        this.userDTOConverter = userDTOConverter;
        this.reverseUserDTOConverter = reverseUserDTOConverter;
        this.userToUserNestedDtoConverter = userToUserNestedDtoConverter;
    }

    public UserDto getUserDto(Long id) {
        return userDTOConverter.convert(userService.getUser(id));
    }

    public User getUser(Long id) {
        return userService.getUser(id);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream().map(userDTOConverter::convert).collect(Collectors.toList());
    }

    public UserDto addUser(UserDto userDto) {
        User user = userService.addUser(reverseUserDTOConverter.convert(userDto));
        return convertUserToUserDto(user);
    }

    public UserDto addUser(User user) {
       User newUser = userService.addUser(user);
       return convertUserToUserDto(user);
    }

    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    public UserDto findUserByEmail(String email) {
        return userDTOConverter.convert(userService.findUserByEmail(email));
    }

    public UserDto updateUser(UserDto userDto) {
        User user = userService.updateUser(reverseUserDTOConverter.convert(userDto));
        return convertUserToUserDto(user);
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

    public UserNestedDto convertToUserNestedDto(User user){
        return userToUserNestedDtoConverter.convert(user);
    }
}
