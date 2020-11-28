
package com.senla.converters;

import com.senla.dto.UserDto;
import com.senla.entity.Community;
import com.senla.entity.User;
import com.senla.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoToUser implements Converter<UserDto, User> {

    private CommunityService communityService;
    private PostService postService;
    private ProfileService profileService;
    private RoleService roleService;
    private LikeService likeService;
    private MessageService messageService;
    private DialogService dialogService;

    @Autowired
    public UserDtoToUser(CommunityService communityService, PostService postService, ProfileService profileService,
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
        List<Community> communities = null;
        Community community = null;
        return User.builder()
                .id(userDto.getId())
                .creationTime(userDto.getCreationTime())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .userName(userDto.getUserName())
                .communities(userDto.getCommunity() == null ? communities : userDto.getCommunities().stream().map(c -> communityService.getCommunity(c.getId())).collect(Collectors.toList()))
                .community(userDto.getCommunity() == null ? community : communityService.getCommunity(userDto.getCommunity().getId()))
                .likes(userDto.getLikes().stream().map(l -> likeService.getLike(l.getId())).collect(Collectors.toList()))
                .profile(profileService.getProfile(userDto.getProfile().getId()))
                .messages(userDto.getMessages().stream().map(m -> messageService.getMessage(m.getId())).collect(Collectors.toList()))
                .posts(userDto.getPosts().stream().map(p -> postService.getPost(p.getId())).collect(Collectors.toList()))
                .roles(userDto.getRoles().stream().map(r -> roleService.getRoleByName(r.getName())).collect(Collectors.toSet()))
                .dialogs(userDto.getDialogs().stream().map(r -> dialogService.getDialog(r.getId())).collect(Collectors.toList()))
                .build();
    }
}

