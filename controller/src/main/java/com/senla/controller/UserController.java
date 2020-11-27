package com.senla.controller;

import com.senla.dto.UserDto;
import com.senla.entity.User;
import com.senla.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users/")
public class UserController {

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }


    @GetMapping(value = "id")
    public ResponseEntity<UserDto> getUserById(){
        UserDto userDto = userFacade.getUserDtoFromSecurityContext();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteUser() {
        User user = userFacade.getUserFromSecurityContext();
        userFacade.deleteUser(user.getId());
        return ResponseEntity.ok()
                .body("You have deleted user successfully");

    }

    @PostMapping("add")
    public UserDto register(@RequestBody UserDto userDto) {
        userFacade.addUser(userDto);
        return userDto;
    }

    @PutMapping("update")
    public UserDto update(@RequestBody UserDto userDto) {
        userFacade.updateUser(userDto);
        return userDto;
    }

    @GetMapping(value = "edit/password")
    public ResponseEntity<String> changePassword(@RequestParam(name = "newPassword") String newPassword) {
        User user = userFacade.getUserFromSecurityContext();
        userFacade.changeUserPassword(newPassword, user.getId());
        return ResponseEntity.ok()
                .body("You have changed password successfully");
    }

    @GetMapping(value = "find/username")
    public ResponseEntity<UserDto> getUserByUserName(@RequestParam(name = "userName") String userName) {
        UserDto userDto = userFacade.findUserByUserName(userName);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
