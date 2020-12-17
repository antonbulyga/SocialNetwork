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

    private final PostToPostForUserAndCommunityDtoConverter postToPostForUserAndCommunityDtoConverter;
    private final CommunityToCommunityForUserDtoConverter communityToCommunityForUserDtoConverter;
    private final MessageToMessageForListDtoConverter messageToMessageForListDtoConverter;
    private final LikeToLikeForPostAndUserDtoConverter likeToLikeForPostAndUserDtoConverter;
    private final RoleToRoleForProfileDtoConverter roleToRoleForProfileDtoConverter;
    private final ProfileToProfileForUserDtoConverter profileToProfileForUserDtoConverter;
    private final DialogToDialogForMessageAndUserDtoConverter dialogToDialogForMessageAndUserDtoConverter;

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
                .email(user.getEmail())
                .userName(user.getUserName())
                .password(user.getPassword())
                .profile(user.getProfile() == null ? profile : profileToProfileForUserDtoConverter.convert(user.getProfile()))
                .communities(user.getCommunities().stream().map(communityToCommunityForUserDtoConverter::convert).collect(Collectors.toList()))
                .roles(user.getRoles().stream().map(roleToRoleForProfileDtoConverter::convert).collect(Collectors.toList()))
                .posts(user.getPosts().stream().map(postToPostForUserAndCommunityDtoConverter::convert).collect(Collectors.toList()))
                .messages(user.getMessages().stream().map(messageToMessageForListDtoConverter::convert).collect(Collectors.toList()))
                .likes(user.getLikes().stream().map(likeToLikeForPostAndUserDtoConverter::convert).collect(Collectors.toList()))
                .creationTime(user.getCreationTime())
                .communitiesWhereUserAdmin(user.getCommunitiesWhereUserAdmin().stream().map(communityToCommunityForUserDtoConverter::convert).collect(Collectors.toList()))
                .dialogs(user.getDialogs().stream().map(dialogToDialogForMessageAndUserDtoConverter::convert).collect(Collectors.toList()))
                .build();
    }
}
