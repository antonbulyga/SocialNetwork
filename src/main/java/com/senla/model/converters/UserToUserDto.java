package com.senla.model.converters;

import com.senla.model.dto.CommunityShortDto;
import com.senla.model.dto.LikeShortDto;
import com.senla.model.dto.ProfileShortDto;
import com.senla.model.dto.UserDto;
import com.senla.model.entity.Like;
import com.senla.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserToUserDto implements Converter<User, UserDto> {
    private PostToPostShortDto postToPostShortDto;
    private CommunityToCommunityShortDto communityToCommunityShortDto;
    private MessageToMessageShortDto messageToMessageShortDto;
    private LikeToLikeShortDto likeToLikeShortDto;
    private RoleToRoleShortDto roleToRoleShortDto;
    private ProfileToProfileShortDto profileToProfileShortDto;
    private DialogToDialogShortDto dialogToDialogShortDto;

    @Autowired
    public UserToUserDto(PostToPostShortDto postToPostShortDto, CommunityToCommunityShortDto communityToCommunityShortDto,
                         MessageToMessageShortDto messageToMessageShortDto, LikeToLikeShortDto likeToLikeShortDto,
                         RoleToRoleShortDto roleToRoleShortDto, ProfileToProfileShortDto profileToProfileShortDto,
                         DialogToDialogShortDto dialogToDialogShortDto) {
        this.postToPostShortDto = postToPostShortDto;
        this.communityToCommunityShortDto = communityToCommunityShortDto;
        this.messageToMessageShortDto = messageToMessageShortDto;
        this.likeToLikeShortDto = likeToLikeShortDto;
        this.roleToRoleShortDto = roleToRoleShortDto;
        this.profileToProfileShortDto = profileToProfileShortDto;
        this.dialogToDialogShortDto = dialogToDialogShortDto;
    }

    @Override
    public UserDto convert(User user) {
        if(user == null){
            return null;
        }
        CommunityShortDto community = null;
        ProfileShortDto profile = null;
        return UserDto.builder()
                .id(user.getId())
                .email(user.getUserName())
                .userName(user.getUserName())
                .password(user.getPassword())
                .profile(user.getProfile() == null ? profile :  profileToProfileShortDto.convert(user.getProfile()))
                .communities(user.getCommunities().stream().map(c -> communityToCommunityShortDto.convert(c)).collect(Collectors.toList()))
                .roles(user.getRoles().stream().map(r -> roleToRoleShortDto.convert(r)).collect(Collectors.toList()))
                .posts(user.getPosts().stream().map(p -> postToPostShortDto.convert(p)).collect(Collectors.toList()))
                .messages(user.getMessages().stream().map(m -> messageToMessageShortDto.convert(m)).collect(Collectors.toList()))
                .likes(user.getLikes().stream().map(l -> likeToLikeShortDto.convert(l)).collect(Collectors.toList()))
                .creationTime(user.getCreationTime())
                .community(user.getCommunity() == null ? community : communityToCommunityShortDto.convert(user.getCommunity()))
                .dialogs(user.getDialogs().stream().map(d -> dialogToDialogShortDto.convert(d)).collect(Collectors.toList()))
                .build();
    }
}
