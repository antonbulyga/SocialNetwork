package com.senla.controller;

import com.senla.dto.user.UserDto;
import com.senla.entity.User;
import com.senla.exception.RestError;
import com.senla.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * * @author  Anton Bulyha
 * * @version 1.0
 * * @since   2020-08-12
 */
@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UserController {

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     * Get User by id
     *
     * @return user dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/myuser")
    public ResponseEntity<UserDto> getUserById() {
        UserDto userDto = userFacade.getUserDtoFromSecurityContext();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Delete user
     *
     * @return string response
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser() {
        User user = userFacade.getUserFromSecurityContext();
        userFacade.deleteUser(user.getId());
        return ResponseEntity.ok()
                .body("You have deleted user successfully");

    }

    /**
     * Add user
     *
     * @param userDto user dto
     * @return user dto
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
        UserDto userDtoWithDate = userFacade.addUser(userDto);
        return new ResponseEntity<>(userDtoWithDate, HttpStatus.OK);
    }

    /**
     * Update user
     *
     * @param userDto
     * @return
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {
        User user = userFacade.getUserFromSecurityContext();
        if (user.getId().equals(userDto.getId())) {
            UserDto userDtoWithDate = userFacade.updateUser(userDto);
            return new ResponseEntity<>(userDtoWithDate, HttpStatus.OK);
        }
        log.warn("You are trying to update someone else's user");
        throw new RestError("You are trying to update someone else's user");

    }

    /**
     * Change user password
     *
     * @param newPassword
     * @return
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/edit/password")
    public ResponseEntity<String> changeUserPassword(@RequestParam(name = "newPassword") String newPassword) {
        User user = userFacade.getUserFromSecurityContext();
        userFacade.changeUserPassword(newPassword, user.getId());
        return ResponseEntity.ok()
                .body("You have changed password successfully");
    }

    /**
     * Get user by name
     *
     * @param userName user name
     * @return user dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/find/username")
    public ResponseEntity<UserDto> getUserByUserName(@RequestParam(name = "userName") String userName) {
        UserDto userDto = userFacade.findUserByUserName(userName);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
