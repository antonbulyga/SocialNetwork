package com.senla.controller;

import com.senla.dto.*;
import com.senla.entity.Profile;
import com.senla.facade.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/")
@Slf4j
public class AdminController {

    private final UserFacade userFacade;
    private final CommunityFacade communityFacade;
    private final DialogFacade dialogFacade;
    private final LikeFacade likeFacade;
    private final MessageFacade messageFacade;
    private final PostFacade postFacade;
    private final ProfileFacade profileFacade;
    private final RoleFacade roleFacade;

    @Autowired
    public AdminController(UserFacade userFacade, CommunityFacade communityFacade, DialogFacade dialogFacade,
                           LikeFacade likeFacade, MessageFacade messageFacade,
                           PostFacade postFacade, ProfileFacade profileFacade, RoleFacade roleFacade) {
        this.userFacade = userFacade;
        this.communityFacade = communityFacade;
        this.dialogFacade = dialogFacade;
        this.likeFacade = likeFacade;
        this.messageFacade = messageFacade;
        this.postFacade = postFacade;
        this.profileFacade = profileFacade;
        this.roleFacade = roleFacade;
    }

    @GetMapping(value = "users")
    public ResponseEntity<List<UserDto>> getAllUsersAsAdmin() {
        List<UserDto> usersDtoList = userFacade.getAllUsers();
        log.info("Getting all users as admin");
        if (usersDtoList.isEmpty()) {
            log.warn("No users");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usersDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserDto> getUserByIdAsAdmin(@PathVariable(name = "id") Long id) {
        UserDto userDto = userFacade.getUser(id);
        log.info("Getting user by id as admin");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("users/delete")
    public ResponseEntity<String> deleteUserAsAdmin(@RequestParam(name = "id") Long userId) {
        userFacade.deleteUser(userId);
        log.info("Deleting user by id as admin");
        return ResponseEntity.ok()
                .body("You have deleted user successfully");

    }

    @GetMapping(value = "users/search/email")
    public ResponseEntity<UserDto> findByEmailAsAdmin (@RequestParam(name = "email") String email) {
        UserDto userDto = userFacade.findUserByEmail(email);
        log.info("Finding user by email as admin");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping(value = "users/edit/password")
    public ResponseEntity<UserDto> changePasswordAsAdmin(@RequestParam(name = "newPassword") String newPassword, @PathVariable(name = "id") long userId) {
        UserDto userDto = userFacade.changeUserPassword(newPassword, userId);
        log.info("You have changed password successfully");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    @GetMapping(value = "roles")
    public ResponseEntity<List<RoleDto>> getAllRolesAsAdmin() {
        List<RoleDto> roleDtoList = roleFacade.getAllRoles();
        log.info("Getting all roles as admin");
        return new ResponseEntity<>(roleDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "role/add")
    public ResponseEntity<RoleDto> addRole(@Valid @RequestBody RoleDto roleDto) {
        roleFacade.addRole(roleDto);
        log.info("Adding role as admin");
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "role/delete")
    public ResponseEntity<String> deleteRole(@Valid @RequestBody RoleDto roleDto) {
        roleFacade.deleteRole(roleDto.getId());
        log.info("Deleting role as admin");
        return ResponseEntity.ok()
                .body("You have deleted the role successfully");
    }

    @PutMapping(value = "role/update")
    public ResponseEntity<RoleDto> update(@Valid @RequestBody RoleDto roleDto) {
        roleFacade.updateRole(roleDto);
        log.info("Updating role as admin");
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @GetMapping(value = "role/search/name")
    public ResponseEntity<RoleDto> getRoleByName(@RequestParam(name = "name") String name) {
        RoleDto roleDto = roleFacade.getRoleByName(name);
        log.info("Getting role by name as admin");
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @GetMapping(value = "profiles")
    public ResponseEntity<List<ProfileDto>> getAllProfilesAsAdmin() {
        List<ProfileDto> dtoList = profileFacade.getAllProfiles();
        log.info("Getting all profiles as admin");
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping(value = "profiles/delete/{id}")
    public ResponseEntity<String> deleteProfileAsAdmin(@PathVariable(name = "id") long id) {
        profileFacade.getProfile(id);
        profileFacade.deleteProfiles(id);
        log.info("Deleting profile as admin");
        return ResponseEntity.ok()
                .body("You have deleted the profile successfully");
    }

    @GetMapping(value = "profiles/search/user")
    public ResponseEntity<ProfileDto> findProfileByUser_IdAsAdmin(@RequestParam(name = "user") Long userId) {
        Profile profile = profileFacade.findProfileByUser_Id(userId);
        log.info("Finding profile by user id as admin");
        ProfileDto profileDto = profileFacade.convertProfileDtoFromProfile(profile);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @PutMapping(value = "profiles/update")
    public ResponseEntity<ProfileDto> updateProfileAsAdmin(@Valid @RequestBody ProfileDto profileDto) {
        profileFacade.updateProfile(profileDto);
        log.info("Updating profile as admin");
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @GetMapping(value = "posts/{id}")
    public ResponseEntity<ProfileDto> getProfileByIdAsAdmin(@PathVariable(name = "id") Long profileId) {
        ProfileDto profileDto = profileFacade.getProfile(profileId);
        log.info("Getting profile by id as admin");
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "posts/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        postFacade.getPost(id);
        postFacade.deletePost(id);
        log.info("Deleting post by id as admin");
        return ResponseEntity.ok()
                .body("You have deleted the post successfully");
    }

    @PutMapping(value = "posts/update")
    public ResponseEntity<PostDto> updatePostAsAdmin(@RequestBody PostDto postDto) {
        postFacade.updatePost(postDto);
        log.info("Updating post by id as admin");
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping(value = "messages")
    public ResponseEntity<List<MessageDto>> getAllMessagesAsAdmin() {
        List<MessageDto> messageDtoList = messageFacade.getAllMessages();
        log.info("Getting all messages as admin");
        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "messages/add")
    public ResponseEntity<MessageDto> addMessageAsAdmin(@RequestBody MessageDto messageDto) {
        messageFacade.addMessage(messageDto);
        log.info("Adding message as admin");
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "messages/delete")
    public ResponseEntity<String> deleteMessageAsAdmin(@RequestParam(name = "id") long id) {
        messageFacade.deleteMessage(id);
        log.info("Deleting message as admin");
        return ResponseEntity.ok()
                .body("You have deleted message successfully");
    }

    @GetMapping(value = "messages/search/dialog/{id}")
    public ResponseEntity<List<MessageDto>> getMessagesByDialog_Id(@PathVariable(name = "id") long id) {
        List<MessageDto> messageDtoList = messageFacade.getMessagesByDialog_Id(id);
        log.info("Getting messages by dialog id as admin");
        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<MessageDto> getMessageByIdAsAdmin(@PathVariable(name = "id") Long messageId) {
        MessageDto messageDto = messageFacade.getMessageDto(messageId);
        log.info("Getting messages by id as admin");
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @GetMapping(value = "likes")
    public ResponseEntity<List<LikeDto>> getAllLikes() {
        List<LikeDto> likeDtoList = likeFacade.getAllLikes();
        log.info("Getting all likes as admin");
        return new ResponseEntity<>(likeDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "dialogs")
    public ResponseEntity<List<DialogDto>> getAllDialogsAsAdmin() {
        List<DialogDto> dialogDtoList = dialogFacade.getAllDialogs();
        log.info("Getting all dialogs as admin");
        return new ResponseEntity<>(dialogDtoList, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deleteCommunity(@RequestParam(name = "id") long id) {
        communityFacade.deleteCommunity(id);
        log.info("Deleting community as admin");
        return ResponseEntity.ok()
                .body("You have deleted community successfully");
    }
}
