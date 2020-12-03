package com.senla.controller;

import com.senla.dto.UserDto;
import com.senla.entity.User;
import com.senla.exception.RestError;
import com.senla.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users/")
@Slf4j
public class UserController {

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }


    @GetMapping(value = "id")
    public ResponseEntity<UserDto> getUserById() {
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
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
        UserDto userDtoWithDate = userFacade.addUser(userDto);
        return new ResponseEntity<>(userDtoWithDate, HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto) {
        User user = userFacade.getUserFromSecurityContext();
        if (user.getId().equals(userDto.getId())) {
            UserDto userDtoWithDate = userFacade.updateUser(userDto);
            return new ResponseEntity<>(userDtoWithDate, HttpStatus.OK);
        } else {
            log.warn("You are trying to update someone else's user");
            throw new RestError("You are trying to update someone else's user");
        }
    }


    @PostMapping(value = "edit/password")
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
