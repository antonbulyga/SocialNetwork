package com.senla.controller;

import com.senla.converters.*;
import com.senla.dto.*;
import com.senla.entity.*;
import com.senla.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/admin/")
public class AdminController {
    private UserService userService;
    private UserToUserDto userToUserDto;
    private RoleService roleService;
    private RoleDtoToRole roleDtoToRole;
    private RoleToRoleDto roleToRoleDto;
    private ProfileService profileService;
    private ProfileToProfileDto profileToProfileDto;
    private PostService postService;
    private PostDtoToPost postDtoToPost;
    private MessageService messageService;
    private MessageToMessageDto messageToMessageDto;
    private MessageDtoToMessage messageDtoToMessage;
    private ProfileDtoToProfile profileDtoToProfile;
    private LikeService likeService;
    private LikeToLikeDto likeToLikeDto;
    private DialogService dialogService;
    private DialogToDialogDto dialogToDialogDto;
    private CommunityService communityService;

    @Autowired
    public AdminController(UserService userService, UserToUserDto userToUserDto, RoleService roleService,
                           RoleDtoToRole roleDtoToRole, RoleToRoleDto roleToRoleDto, ProfileService profileService,
                           ProfileToProfileDto profileToProfileDto, PostService postService,
                           PostDtoToPost postDtoToPost, MessageService messageService, MessageToMessageDto messageToMessageDto,
                           MessageDtoToMessage messageDtoToMessage, ProfileDtoToProfile profileDtoToProfile, LikeService likeService, LikeToLikeDto likeToLikeDto,
                           DialogService dialogService,DialogToDialogDto dialogToDialogDto, CommunityService communityService) {
        this.userService = userService;
        this.userToUserDto = userToUserDto;
        this.roleService = roleService;
        this.roleDtoToRole = roleDtoToRole;
        this.roleToRoleDto = roleToRoleDto;
        this.profileService = profileService;
        this.profileToProfileDto = profileToProfileDto;
        this.postService = postService;
        this.postDtoToPost = postDtoToPost;
        this.messageService = messageService;
        this.messageToMessageDto = messageToMessageDto;
        this.messageDtoToMessage = messageDtoToMessage;
        this.profileDtoToProfile = profileDtoToProfile;
        this.likeService = likeService;
        this.likeToLikeDto = likeToLikeDto;
        this.dialogService = dialogService;
        this.dialogToDialogDto = dialogToDialogDto;
        this.communityService = communityService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserDto> getUserByIdAsAdmin(@PathVariable(name = "id") Long id){
        User user = userService.getUserById(id);
        UserDto userDto = userToUserDto.convert(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("users/delete")
    public ResponseEntity<String> deleteUserAsAdmin(@RequestParam (name = "id") Long userId) {
        userService.getUser(userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok()
                .body("You have deleted user successfully");

    }

    @GetMapping(value = "users/search/email")
    public ResponseEntity<UserDto> findByEmailAsAdmin(@RequestParam(name = "email") String email) {
        User user = userService.findUserByEmail(email);
        UserDto userDto = userToUserDto.convert(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping(value = "users/edit/password")
    public ResponseEntity<String> changePasswordAsAdmin(@RequestParam(name = "newPassword") String newPassword,@PathVariable(name = "id") long userId) {
        User user = userService.changeUserPassword(newPassword, userId);
        return ResponseEntity.ok()
                .body("You have changed password successfully");
    }


    @GetMapping(value = "users")
    public ResponseEntity<List<RoleDto>> getAllUsersAsAdmin(){
        List<Role> roles = roleService.getAllRoles();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            RoleDto result = roleToRoleDto.convert(roles.get(i));
            roleDtoList.add(result);
        }
        return new ResponseEntity<>(roleDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "role/add")
    public ResponseEntity<RoleDto> addRole(@RequestBody RoleDto roleDto) {
        Role role = roleDtoToRole.convert(roleDto);
        roleService.addRole(role);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "role/delete")
    public ResponseEntity<String> deleteRole(@RequestBody RoleDto roleDto) {
        roleService.deleteRole(roleDto.getId());
        return ResponseEntity.ok()
                .body("You have deleted the role successfully");
    }

    @PostMapping(value = "role/update")
    public ResponseEntity<RoleDto> update(@RequestBody RoleDto roleDto) {
        Role role = roleDtoToRole.convert(roleDto);
        roleService.editRole(role);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @GetMapping(value = "role/search/name")
    public ResponseEntity<RoleDto> getRoleByName(@RequestParam(name = "name") String name) {
        Role role = roleService.getRoleByName(name);
        RoleDto roleDto = roleToRoleDto.convert(role);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @GetMapping(value = "profiles")
    public ResponseEntity<List<ProfileDto>> getAllProfilesAsAdmin(){
        List<ProfileDto> dtoList = new ArrayList<>();
        List<Profile> profiles = profileService.getAllProfiles();

        for (int i = 0; i < profiles.size(); i++) {
            ProfileDto profileDto = profileToProfileDto.convert(profiles.get(i));
            dtoList.add(profileDto);
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping(value = "profiles/delete/{id}")
    public ResponseEntity<String> deleteProfileAsAdmin(@PathVariable(name = "id") long id) {
        profileService.getProfile(id);

        profileService.deleteProfiles(id);
        return ResponseEntity.ok()
                .body("You have deleted the profile successfully");
    }

    @GetMapping(value = "profiles/search/user")
    public ResponseEntity<ProfileDto> findProfileByUser_IdAsAdmin(@RequestParam(name = "user") Long userId) {
        Profile profile = profileService.findProfileByUser_Id(userId);

        ProfileDto profileDto = profileToProfileDto.convert(profile);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @PostMapping(value = "profiles/update")
    public ResponseEntity<ProfileDto> updateProfileAsAdmin(@RequestBody ProfileDto profileDto) {
        Profile profile = profileDtoToProfile.convert(profileDto);
        profileService.updateProfile(profile);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @GetMapping(value = "posts/{id}")
    public ResponseEntity<ProfileDto> getProfileByIdAsAdmin(@PathVariable(name = "id") Long profileId) {
        Profile profile = profileService.getProfile(profileId);
        ProfileDto profileDto = profileToProfileDto.convert(profile);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "posts/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable (name = "id") long id) {
        postService.getPost(id);

        postService.deletePost(id);
        return ResponseEntity.ok()
                .body("You have deleted the post successfully");
    }

    @PostMapping(value = "posts/update")
    public ResponseEntity<PostDto> updatePostAsAdmin (@RequestBody PostDto postDto){
        Post post = postDtoToPost.convert(postDto);
        postService.editPost(post);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping(value = "messages")
    public ResponseEntity<List<MessageDto>> getAllMessagesAsAdmin(){
        List<Message> messageList = messageService.getAllMessages();
        List<MessageDto> messageDtoList = new ArrayList<>();

        for (int i = 0; i < messageList.size(); i++) {
            MessageDto result = messageToMessageDto.convert(messageList.get(i));
            messageDtoList.add(result);
        }
        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "messages/add")
    public ResponseEntity<MessageDto> addMessageAsAdmin(@RequestBody MessageDto messageDto) {
        Message message = messageDtoToMessage.convert(messageDto);
        messageService.addMessage(message);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "messages/delete")
    public ResponseEntity<String> deleteMessageAsAdmin(@RequestParam (name = "id") long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok()
                .body("You have deleted message successfully");
    }

    @GetMapping(value = "messages/search/dialog/{id}")
    public ResponseEntity<List<MessageDto>> getMessagesByDialog_Id(@PathVariable (name = "id") long id) {
        List<Message> messages = messageService.getMessagesByDialog_Id(id);
        List<MessageDto> messageDtoList = messages.stream().map(message -> messageToMessageDto.convert(message)).collect(Collectors.toList());
        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<MessageDto> getMessageByIdAsAdmin(@PathVariable(name = "id") Long messageId) {
        Message message = messageService.getMessage(messageId);
        MessageDto messageDto = messageToMessageDto.convert(message);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @GetMapping(value = "likes")
    public ResponseEntity<List<LikeDto>> getAllLikes(){
        List<Like> likeList = likeService.getAll();
        List<LikeDto> likeDtoList = new ArrayList<>();

        for (int i = 0; i < likeList.size(); i++) {
            LikeDto result = likeToLikeDto.convert(likeList.get(i));
            likeDtoList.add(result);
        }
        return new ResponseEntity<>(likeDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "dialogs")
    public ResponseEntity<List<DialogDto>> getAllDialogsAsAdmin(){
        List<Dialog> dialogList = dialogService.getAll();
        List<DialogDto> dialogDtoList = new ArrayList<>();

        for (int i = 0; i < dialogList.size(); i++) {
            DialogDto result = dialogToDialogDto.convert(dialogList.get(i));
            dialogDtoList.add(result);
        }
        return new ResponseEntity<>(dialogDtoList, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deleteCommunity(@RequestParam (name = "id") long id) {
        communityService.getCommunity(id);
        communityService.delete(id);
        return ResponseEntity.ok()
                .body("You have deleted community successfully");
    }
}
