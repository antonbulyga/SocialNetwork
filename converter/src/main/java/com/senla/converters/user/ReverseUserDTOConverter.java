
package com.senla.converters.user;

import com.senla.dto.user.UserDto;
import com.senla.entity.Community;
import com.senla.entity.Profile;
import com.senla.entity.User;
import com.senla.service.community.CommunityService;
import com.senla.service.dialog.DialogService;
import com.senla.service.like.LikeService;
import com.senla.service.message.MessageService;
import com.senla.service.post.PostService;
import com.senla.service.profile.ProfileService;
import com.senla.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReverseUserDTOConverter implements Converter<UserDto, User> {

    private final CommunityService communityService;
    private final PostService postService;
    private final ProfileService profileService;
    private final RoleService roleService;
    private final LikeService likeService;
    private final MessageService messageService;
    private final DialogService dialogService;

    @Autowired
    public ReverseUserDTOConverter(CommunityService communityService, PostService postService, ProfileService profileService,
                                   RoleService roleService, LikeService likeService,
                                   MessageService messageService, DialogService dialogService) {
        this.communityService = communityService;
        this.postService = postService;
        this.profileService = profileService;
        this.roleService = roleService;
        this.likeService = likeService;
        this.messageService = messageService;
        this.dialogService = dialogService;
    }

    @Override
    public User convert(UserDto userDto) {
        Community community = null;
        Profile profile = null;
        return User.builder()
                .id(userDto.getId())
                .creationTime(LocalDateTime.now())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .userName(userDto.getUserName())
                .communities(userDto.getCommunities().stream().map(c -> communityService.getCommunity(c.getId())).collect(Collectors.toList()))
                .communitiesWhereUserAdmin(userDto.getCommunitiesWhereUserAdmin().stream().map(c -> communityService.getCommunity(c.getId())).collect(Collectors.toList()))
                .likes(userDto.getLikes().stream().map(l -> likeService.getLike(l.getId())).collect(Collectors.toList()))
                .profile(userDto.getProfile() == null ? profile : profileService.getProfile(userDto.getProfile().getId()))
                .messages(userDto.getMessages().stream().map(m -> messageService.getMessage(m.getId())).collect(Collectors.toList()))
                .posts(userDto.getPosts().stream().map(p -> postService.getPost(p.getId())).collect(Collectors.toList()))
                .roles(userDto.getRoles().stream().map(r -> roleService.getRoleByName(r.getName())).collect(Collectors.toSet()))
                .dialogs(userDto.getDialogs().stream().map(r -> dialogService.getDialog(r.getId())).collect(Collectors.toList()))
                .build();
    }
}

