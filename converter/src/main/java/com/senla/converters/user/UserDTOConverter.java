package com.senla.converters.user;

import com.senla.converters.community.CommunityToCommunityForUserDtoConverter;
import com.senla.converters.dialog.DialogToDialogForMessageAndUserDtoConverter;
import com.senla.converters.like.LikeToLikeForPostAndUserDtoConverter;
import com.senla.converters.message.MessageToMessageForListDtoConverter;
import com.senla.converters.post.PostToPostForUserAndCommunityDtoConverter;
import com.senla.converters.profile.ProfileToProfileForUserDtoConverter;
import com.senla.converters.role.RoleToRoleForProfileDtoConverter;
import com.senla.dto.community.CommunityForUserDto;
import com.senla.dto.profile.ProfileForUserDto;
import com.senla.dto.user.UserDto;
import com.senla.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDTOConverter implements Converter<User, UserDto> {

    private PostToPostForUserAndCommunityDtoConverter postToPostForUserAndCommunityDtoConverter;
    private CommunityToCommunityForUserDtoConverter communityToCommunityForUserDtoConverter;
    private MessageToMessageForListDtoConverter messageToMessageForListDtoConverter;
    private LikeToLikeForPostAndUserDtoConverter likeToLikeForPostAndUserDtoConverter;
    private RoleToRoleForProfileDtoConverter roleToRoleForProfileDtoConverter;
    private ProfileToProfileForUserDtoConverter profileToProfileForUserDtoConverter;
    private DialogToDialogForMessageAndUserDtoConverter dialogToDialogForMessageAndUserDtoConverter;

    @Autowired
    public UserDTOConverter(PostToPostForUserAndCommunityDtoConverter postToPostForUserAndCommunityDtoConverter, CommunityToCommunityForUserDtoConverter communityToCommunityForUserDtoConverter,
                            MessageToMessageForListDtoConverter messageToMessageForListDtoConverter, LikeToLikeForPostAndUserDtoConverter likeToLikeForPostAndUserDtoConverter,
                            RoleToRoleForProfileDtoConverter roleToRoleForProfileDtoConverter, ProfileToProfileForUserDtoConverter profileToProfileForUserDtoConverter,
                            DialogToDialogForMessageAndUserDtoConverter dialogToDialogForMessageAndUserDtoConverter) {
        this.postToPostForUserAndCommunityDtoConverter = postToPostForUserAndCommunityDtoConverter;
        this.communityToCommunityForUserDtoConverter = communityToCommunityForUserDtoConverter;
        this.messageToMessageForListDtoConverter = messageToMessageForListDtoConverter;
        this.likeToLikeForPostAndUserDtoConverter = likeToLikeForPostAndUserDtoConverter;
        this.roleToRoleForProfileDtoConverter = roleToRoleForProfileDtoConverter;
        this.profileToProfileForUserDtoConverter = profileToProfileForUserDtoConverter;
        this.dialogToDialogForMessageAndUserDtoConverter = dialogToDialogForMessageAndUserDtoConverter;
    }

    @Override
    public UserDto convert(User user) {
        if (user == null) {
            return null;
        }
        CommunityForUserDto community = null;
        ProfileForUserDto profile = null;
        return UserDto.builder()
                .id(user.getId())
                .email(user.getUserName())
                .userName(user.getUserName())
                .password(user.getPassword())
                .profile(user.getProfile() == null ? profile : profileToProfileForUserDtoConverter.convert(user.getProfile()))
                .communities(user.getCommunities().stream().map(c -> communityToCommunityForUserDtoConverter.convert(c)).collect(Collectors.toList()))
                .roles(user.getRoles().stream().map(r -> roleToRoleForProfileDtoConverter.convert(r)).collect(Collectors.toList()))
                .posts(user.getPosts().stream().map(p -> postToPostForUserAndCommunityDtoConverter.convert(p)).collect(Collectors.toList()))
                .messages(user.getMessages().stream().map(m -> messageToMessageForListDtoConverter.convert(m)).collect(Collectors.toList()))
                .likes(user.getLikes().stream().map(l -> likeToLikeForPostAndUserDtoConverter.convert(l)).collect(Collectors.toList()))
                .creationTime(user.getCreationTime())
                .community(user.getCommunity() == null ? community : communityToCommunityForUserDtoConverter.convert(user.getCommunity()))
                .dialogs(user.getDialogs().stream().map(d -> dialogToDialogForMessageAndUserDtoConverter.convert(d)).collect(Collectors.toList()))
                .build();
    }
}
