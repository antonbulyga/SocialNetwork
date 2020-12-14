package com.senla.controller;

import com.senla.dto.community.CommunityDto;
import com.senla.dto.dialog.DialogDto;
import com.senla.dto.friendship.FriendshipDto;
import com.senla.dto.like.LikeDto;
import com.senla.dto.message.MessageDto;
import com.senla.dto.post.PostDto;
import com.senla.dto.profile.ProfileDto;
import com.senla.dto.role.RoleDto;
import com.senla.dto.user.UserDto;
import com.senla.entity.*;
import com.senla.exception.RestError;
import com.senla.facade.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * * @author  Anton Bulyha
 * * @version 1.0
 * * @since   2020-08-12
 */
@RestController
@RequestMapping(value = "/admin")
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
    private final FriendshipFacade friendshipFacade;

    @Autowired
    public AdminController(UserFacade userFacade, CommunityFacade communityFacade, DialogFacade dialogFacade,
                           LikeFacade likeFacade, MessageFacade messageFacade,
                           PostFacade postFacade, ProfileFacade profileFacade, RoleFacade roleFacade, FriendshipFacade friendshipFacade) {
        this.userFacade = userFacade;
        this.communityFacade = communityFacade;
        this.dialogFacade = dialogFacade;
        this.likeFacade = likeFacade;
        this.messageFacade = messageFacade;
        this.postFacade = postFacade;
        this.profileFacade = profileFacade;
        this.roleFacade = roleFacade;
        this.friendshipFacade = friendshipFacade;
    }

    /**
     * Get all users
     *
     * @return user dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getAllUsersAsAdmin() {
        List<UserDto> usersDtoList = userFacade.getAllUsers();
        log.info("Getting all users as admin");
        if (usersDtoList.isEmpty()) {
            log.warn("No users");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usersDtoList, HttpStatus.OK);
    }

    /**
     * Get user by id as admin user
     *
     * @param id user id
     * @return user dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserDto> getUserByIdAsAdmin(@PathVariable(name = "id") Long id) {
        UserDto userDto = userFacade.getUser(id);
        log.info("Getting user by id as admin");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Delete user as admin user
     *
     * @param userId user id
     * @return string
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/users/delete")
    public ResponseEntity<String> deleteUserAsAdmin(@RequestParam(name = "id") Long userId) {
        userFacade.deleteUser(userId);
        log.info("Deleting user by id as admin");
        return ResponseEntity.ok()
                .body("You have deleted user successfully");

    }

    /**
     * Find user by email as admin
     *
     * @param email user email
     * @return user dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/users/search/email")
    public ResponseEntity<UserDto> findByEmailAsAdmin(@RequestParam(name = "email") String email) {
        UserDto userDto = userFacade.findUserByEmail(email);
        log.info("Finding user by email as admin");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Change password as admin user
     *
     * @param newPassword new user password
     * @param userId      user id
     * @return user dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/users/edit/password")
    public ResponseEntity<UserDto> changePasswordAsAdmin(@RequestParam(name = "newPassword") String newPassword, @RequestParam(name = "id") long userId) {
        UserDto userDto = userFacade.changeUserPassword(newPassword, userId);
        log.info("You have changed password successfully");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Get all roles as admin
     *
     * @return list of role dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/roles")
    public ResponseEntity<List<RoleDto>> getAllRolesAsAdmin() {
        List<RoleDto> roleDtoList = roleFacade.getAllRoles();
        log.info("Getting all roles as admin");
        return new ResponseEntity<>(roleDtoList, HttpStatus.OK);
    }

    /**
     * Add role
     *
     * @param roleDto role dto
     * @return role dto
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/roles/add")
    public ResponseEntity<RoleDto> addRole(@Valid @RequestBody RoleDto roleDto) {
        roleFacade.addRole(roleDto);
        log.info("Adding role as admin");
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    /**
     * Delete role
     *
     * @param roleDto role dto
     * @return string
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/roles/delete")
    public ResponseEntity<String> deleteRole(@Valid @RequestBody RoleDto roleDto) {
        roleFacade.deleteRole(roleDto.getId());
        log.info("Deleting role as admin");
        return ResponseEntity.ok()
                .body("You have deleted the role successfully");
    }

    /**
     * Update role
     *
     * @param roleDto role dto
     * @return role dto
     */
    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/roles/update")
    public ResponseEntity<RoleDto> update(@Valid @RequestBody RoleDto roleDto) {
        roleFacade.updateRole(roleDto);
        log.info("Updating role as admin");
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    /**
     * Get role by name
     *
     * @param name role name
     * @return role dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/roles/search/name")
    public ResponseEntity<RoleDto> getRoleByName(@RequestParam(name = "name") String name) {
        RoleDto roleDto = roleFacade.getRoleByName(name);
        log.info("Getting role by name as admin");
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    /**
     * Get all profiles as admin user
     *
     * @return list profile dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/profiles")
    public ResponseEntity<List<ProfileDto>> getAllProfilesAsAdmin() {
        List<ProfileDto> dtoList = profileFacade.getAllProfiles();
        log.info("Getting all profiles as admin");
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /**
     * Find user profile by user id as admin user
     *
     * @param userId user id
     * @return profile dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/profiles/search/user")
    public ResponseEntity<ProfileDto> findProfileByUser_IdAsAdmin(@RequestParam(name = "userId") Long userId) {
        Profile profile = profileFacade.findProfileByUser_Id(userId);
        log.info("Finding profile by user id as admin");
        ProfileDto profileDto = profileFacade.convertProfileDtoFromProfile(profile);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    /**
     * Update user profile as admin user
     *
     * @param profileDto profile dto
     * @return profile dto
     */
    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/profiles/update")
    public ResponseEntity<ProfileDto> updateProfileAsAdmin(@Valid @RequestBody ProfileDto profileDto) {
        profileFacade.updateProfile(profileDto);
        log.info("Updating profile as admin");
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    /**
     * Delete post
     *
     * @param id post id
     * @return string
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/posts/delete")
    public ResponseEntity<String> deletePost(@RequestParam(name = "id") long id) {
        postFacade.getPost(id);
        postFacade.deletePost(id);
        log.info("Deleting post by id as admin");
        return ResponseEntity.ok()
                .body("You have deleted the post successfully");
    }

    /**
     * Update post
     *
     * @param postDto post dto
     * @return post dto
     */
    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/posts/update")
    public ResponseEntity<PostDto> updatePostAsAdmin(@Valid @RequestBody PostDto postDto) {
        PostDto postDtoWithDate = postFacade.updatePost(postDto);
        log.info("Updating post by id as admin");
        return new ResponseEntity<>(postDtoWithDate, HttpStatus.OK);
    }

    /**
     * Get all messages as admin user
     *
     * @return list of the messages dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/messages")
    public ResponseEntity<List<MessageDto>> getAllMessagesAsAdmin() {
        List<MessageDto> messageDtoList = messageFacade.getAllMessages();
        log.info("Getting all messages as admin");
        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }

    /**
     * Add message as admin user
     *
     * @param messageDto message dto
     * @return message dto
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/messages/add")
    public ResponseEntity<MessageDto> addMessageAsAdmin(@Valid @RequestBody MessageDto messageDto) {
        log.info("Adding message as admin");
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    /**
     * Delete message as admin user
     *
     * @param id message id
     * @return string
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/messages/delete")
    public ResponseEntity<String> deleteMessageAsAdmin(@RequestParam(name = "id") long id) {
        messageFacade.deleteMessage(id);
        log.info("Deleting message as admin");
        return ResponseEntity.ok()
                .body("You have deleted message successfully");
    }

    /**
     * Get message by dialog id
     *
     * @param id dialog id
     * @return list of the message dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/messages/search/dialog/{id}")
    public ResponseEntity<List<MessageDto>> getMessagesByDialog_Id(@PathVariable(name = "id") Long id) {
        List<MessageDto> messageDtoList = messageFacade.getMessagesByDialog_Id(id);
        log.info("Getting messages by dialog id as admin");
        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }

    /**
     * Get message by id as admin user
     *
     * @param messageId message id
     * @return message dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/messages/{id}")
    public ResponseEntity<MessageDto> getMessageByIdAsAdmin(@PathVariable(name = "id") Long messageId) {
        MessageDto messageDto = messageFacade.getMessageDto(messageId);
        log.info("Getting messages by id as admin");
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    /**
     * Get all likes as admin user
     *
     * @return list of the like dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/likes")
    public ResponseEntity<List<LikeDto>> getAllLikesAsAdmin() {
        List<LikeDto> likeDtoList = likeFacade.getAllLikes();
        log.info("Getting all likes as admin");
        return new ResponseEntity<>(likeDtoList, HttpStatus.OK);
    }

    /**
     * Add like
     *
     * @param likeDto like dto
     * @return like dto
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/likes/add")
    public ResponseEntity<LikeDto> addLike(@Valid @RequestBody LikeDto likeDto) {
        likeFacade.addLike(likeDto);
        log.error("Adding like");
        return new ResponseEntity<>(likeDto, HttpStatus.OK);
    }

    /**
     * Delete like
     *
     * @param id like id
     * @return string
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/likes/delete")
    public ResponseEntity<String> deleteLike(@RequestParam(name = "id") Long id) {
        likeFacade.deleteLike(id);
        log.error("Deleting like");
        return ResponseEntity.ok()
                .body("You have deleted like successfully");

    }

    /**
     * Get all dialogs as admin user
     *
     * @return list of dialog dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/dialogs")
    public ResponseEntity<List<DialogDto>> getAllDialogsAsAdmin() {
        List<DialogDto> dialogDtoList = dialogFacade.getAllDialogs();
        log.info("Getting all dialogs as admin");
        return new ResponseEntity<>(dialogDtoList, HttpStatus.OK);
    }

    /**
     * Add dialog as admin user
     *
     * @param dialogDto dialog dto
     * @return dialog dto
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/dialogs/add")
    public ResponseEntity<DialogDto> addDialogAsAdmin(@Valid @RequestBody DialogDto dialogDto) {
        DialogDto dialogDtoWithData = dialogFacade.addDialog(dialogDto);
        log.info("Adding a new dialog");
        return new ResponseEntity<>(dialogDtoWithData, HttpStatus.OK);
    }

    /**
     * Delete dialog
     *
     * @param id dialog dto
     * @return string
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/dialogs/delete")
    public ResponseEntity<String> deleteDialog(@RequestParam(name = "id") Long id) {
        dialogFacade.deleteDialog(id);
        log.info("Deleting the dialog as admin");
        return ResponseEntity.ok()
                .body("You have deleted dialog successfully");

    }

    /**
     * Get dialog by id
     *
     * @param id dialog id
     * @return dialog dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/dialogs/{id}")
    public ResponseEntity<DialogDto> getDialogById(@PathVariable(name = "id") Long id) {
        DialogDto dialogDto = dialogFacade.getDialogDto(id);
        log.info("Getting the dialog by id as admin");
        return new ResponseEntity<>(dialogDto, HttpStatus.OK);
    }

    /**
     * Update dialog
     *
     * @param dialogDto dialog dto
     * @return dialog dto
     */
    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/dialogs/update")
    public ResponseEntity<DialogDto> updateDialog(@Valid @RequestBody DialogDto dialogDto) {
        DialogDto dialogDtoWithData = dialogFacade.updateDialog(dialogDto);
        log.error("You are updating dialog");
        return new ResponseEntity<>(dialogDtoWithData, HttpStatus.OK);
    }

    /**
     * Get dialog by name
     *
     * @param name dialog name
     * @return dialog dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/dialogs/search/name")
    public ResponseEntity<DialogDto> getDialogByName(@RequestParam(name = "name") String name) {
        DialogDto dialogDtoWithTime = dialogFacade.getDialogByName(name);
        log.error("You are getting dialog by name as admin");
        return new ResponseEntity<>(dialogDtoWithTime, HttpStatus.OK);
    }

    /**
     * Add user to the dialog
     *
     * @param dialogId dialog id
     * @param userId   user id
     * @return dialog dto
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/dialogs/add/user")
    public ResponseEntity<DialogDto> addUserToDialog(@RequestParam(name = "dialogId") Long dialogId, @RequestParam(name = "userId") Long userId) {
        log.error("You are adding user to the dialog");
        DialogDto dialogDtoWithTime = dialogFacade.addUserToDialog(dialogId, userId);
        return new ResponseEntity<>(dialogDtoWithTime, HttpStatus.OK);
    }

    /**
     * Delete user from the dialog
     *
     * @param dialogId dialog id
     * @param userId   user id
     * @return dialog dto
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/dialogs/delete/user")
    public ResponseEntity<DialogDto> deleteUserFromDialog(@RequestParam(name = "dialogId") Long dialogId, @RequestParam(name = "userId") Long userId) {
        log.error("You are deleting user from the dialog");
        DialogDto dialogDtoWithTime = dialogFacade.deleteUserFromDialog(dialogId, userId);
        return new ResponseEntity<>(dialogDtoWithTime, HttpStatus.OK);
    }

    /**
     * Add community
     *
     * @param communityDto community dto
     * @return community dto
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/communities/add")
    public ResponseEntity<CommunityDto> addCommunity(@Valid @RequestBody CommunityDto communityDto) {
        communityFacade.addCommunity(communityDto);
        log.info("Adding community");
        return new ResponseEntity<>(communityDto, HttpStatus.OK);
    }

    /**
     * Add user to the community as admin
     * @param userId user id
     * @param communityId community id
     * @return community dto
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/communities/add/user")
    public ResponseEntity<CommunityDto> addUserToCommunity(@RequestParam(name = "userId") Long userId, @RequestParam(name = "communityId") Long communityId) {
        CommunityDto communityDto = communityFacade.addUserToCommunity(communityId, userId);
        log.info("Adding user to the community");
        return new ResponseEntity<>(communityDto, HttpStatus.OK);
    }

    /**
     * Delete user from the community
     * @param userId user id
     * @param communityId community id
     * @return
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/communities/delete/user")
    public ResponseEntity<String> removeUserFromCommunity(@RequestParam(name = "userId") Long userId, @RequestParam(name = "communityId") Long communityId) {
        communityFacade.removeUserFromCommunity(communityId, userId);
        log.info("Removing user from the community");
        return ResponseEntity.ok()
                .body("You have deleted user from the community successfully");
    }

    /**
     * Update community
     * @param communityDto community dto
     * @return community dto
     */
    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/community/update")
    public ResponseEntity<CommunityDto> updateCommunity(@Valid @RequestBody CommunityDto communityDto) {
        communityFacade.updateCommunity(communityDto);
        log.info("Updating community");
        return new ResponseEntity<>(communityDto, HttpStatus.OK);
    }

    /**
     * Delete community as admin user
     *
     * @param id community id
     * @return string
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/community/delete")
    public ResponseEntity<String> deleteCommunityAsAdmin(@RequestParam(name = "id") long id) {
        communityFacade.deleteCommunity(id);
        log.info("Deleting community as admin");
        return ResponseEntity.ok()
                .body("You have deleted community successfully");
    }

    /**
     * @param userOneId    user one id
     * @param userTwoId    user two id
     * @param actionUserId action user id
     * @return friendship dto
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/friendship/request")
    public ResponseEntity<FriendshipDto> sentFriendRequestAsAdmin(@RequestParam(name = "idOne") Long userOneId,
                                                                  @RequestParam(name = "idTwo") Long userTwoId,
                                                                  @RequestParam(name = "idAction") Long actionUserId) {
        FriendshipDto friendshipDto = friendshipFacade.sentFriendRequest(userOneId, userTwoId, actionUserId);
        log.info("Sending a friend request");
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    /**
     * Add to friends as admin user
     *
     * @param userOneId    user one id
     * @param userTwoId    user two id
     * @param actionUserId action user id
     * @return friendship dto
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/friendship/accept")
    public ResponseEntity<FriendshipDto> addToFriendsAsAdmin(@RequestParam(name = "idOne") Long userOneId,
                                                             @RequestParam(name = "idTwo") Long userTwoId,
                                                             @RequestParam(name = "idAction") Long actionUserId) {
        FriendshipDto friendshipDto = friendshipFacade.addToFriends(userOneId, userTwoId, actionUserId);
        log.info("Adding to friend user");
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/friendship/decline")
    public ResponseEntity<FriendshipDto> declineFriendRequest(@RequestParam(name = "idOne") Long userOneId,
                                                              @RequestParam(name = "idTwo") Long userTwoId,
                                                              @RequestParam(name = "idAction") Long actionUserId) {

        FriendshipDto friendshipDto = friendshipFacade.declineFriendRequest(userOneId, userTwoId, actionUserId);
        log.info("Decline friend request");
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    /**
     * Get friends list of user as admin
     *
     * @param userId user id
     * @return user dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/friendship/friends")
    public ResponseEntity<List<UserDto>> getFriendsListOfUserAsAdmin(@RequestParam(name = "id") Long userId) {
        List<UserDto> userDto = friendshipFacade.getFriendsListOfUser(userId);
        log.info("Getting a friend list of the user");
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/friendship/delete")
    public ResponseEntity<FriendshipDto> deleteFriendshipAsAdmin(@RequestParam(name = "idOne") Long userOneId,
                                                                 @RequestParam(name = "idTwo") Long userTwoId,
                                                                 @RequestParam(name = "idAction") Long actionUserId) {
        FriendshipDto friendshipDto = friendshipFacade.deleteFriendship(userOneId, userTwoId, actionUserId);
        log.info("Deleting the user from the friends");
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    /**
     * Block user as admin user
     *
     * @param userOneId    user one id
     * @param userTwoId    user two id
     * @param actionUserId action user id
     * @return friendship dto
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/friendship/block")
    public ResponseEntity<FriendshipDto> blockUserAsAdmin(@RequestParam(name = "idOne") Long userOneId,
                                                          @RequestParam(name = "idTwo") Long userTwoId,
                                                          @RequestParam(name = "idAction") Long actionUserId) {
        FriendshipDto friendshipDto = friendshipFacade.blockUser(userOneId, userTwoId, actionUserId);
        log.info("Blocking user");
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    /**
     * Unblock user as admin
     *
     * @param userOneId    user one id
     * @param userTwoId    user two id
     * @param actionUserId action user id
     * @return friendship dto
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/friendship/unblock")
    public ResponseEntity<FriendshipDto> unblockUserAsAdmin(@RequestParam(name = "idOne") Long userOneId,
                                                            @RequestParam(name = "idTwo") Long userTwoId,
                                                            @RequestParam(name = "idAction") Long actionUserId) {

        FriendshipDto friendshipDto = friendshipFacade.unblockUser(userOneId, userTwoId, actionUserId);
        log.info("Unlocking user");
        return ResponseEntity.ok()
                .body(friendshipDto);
    }

    /**
     * Get friends requests as admin user
     *
     * @param userId user id
     * @return map string and list of the user dto
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/friendship/requests")
    public ResponseEntity<Map<String, List<UserDto>>> getRequestsAsAdmin(@RequestParam(name = "id") Long userId) {
        List<Friendship> requests = friendshipFacade.getRequests(userId);
        Map<Boolean, List<Friendship>> requestMap = requests.stream()
                .collect(Collectors.partitioningBy((f) -> f.getActionUser().getId() == userId));

        List<UserDto> outgoingFriendRequests = requestMap.get(true).stream()
                .map(Friendship::getActionUser)
                .map(userFacade::convertUserToUserDto)
                .collect(Collectors.toList());

        List<UserDto> incomingFriendRequests = requestMap.get(false).stream()
                .map(Friendship::getActionUser)
                .map(userFacade::convertUserToUserDto)
                .collect(Collectors.toList());

        Map<String, List<UserDto>> map = new HashMap<>();
        map.put("outgoingFriendRequests", outgoingFriendRequests);
        map.put("incomingFriendRequests", incomingFriendRequests);
        log.info("Getting user requests");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
