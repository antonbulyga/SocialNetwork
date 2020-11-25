package com.senla.controller;

import com.senla.converters.UserDtoToUser;
import com.senla.converters.UserToUserDto;
import com.senla.dto.UserDto;
import com.senla.entity.User;
import com.senla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users/")
public class UserController {
    private UserService userService;
    private UserToUserDto userToUserDto;
    private UserDtoToUser userDtoToUser;


    @Autowired
    public UserController(UserService userService, UserToUserDto userToUserDto, UserDtoToUser userDtoToUser ) {
        this.userService = userService;
        this.userToUserDto = userToUserDto;
        this.userDtoToUser = userDtoToUser;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getAll(){
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtoList = new ArrayList<>();
        if(users == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (int i = 0; i < users.size(); i++) {
            UserDto result = userToUserDto.convert(users.get(i));
            userDtoList.add(result);
        }
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "id")
    public ResponseEntity<UserDto> getUserById(){
        User user = userService.getUserFromSecurityContext();
        UserDto userDto = userToUserDto.convert(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        User user = userService.findUserByUserName(userName);
        userService.deleteUser(user.getId());
        return ResponseEntity.ok()
                .body("You have deleted user successfully");

    }

    @PostMapping("add")
    public UserDto register(@RequestBody UserDto userDto) {
        User user = userDtoToUser.convert(userDto);
        userService.addUser(user);
        return userDto;
    }

    @PutMapping("update")
    public UserDto update(@RequestBody UserDto userDto) {
        User user = userDtoToUser.convert(userDto);
        userService.editUser(user);
        return userDto;
    }

    @GetMapping(value = "edit/password")
    public ResponseEntity<String> changePassword(@RequestParam(name = "newPassword") String newPassword) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        User user = userService.findUserByUserName(userName);
        userService.changeUserPassword(newPassword, user.getId());
        return ResponseEntity.ok()
                .body("You have changed password successfully");
    }

}
