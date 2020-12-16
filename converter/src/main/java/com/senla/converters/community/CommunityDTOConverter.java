
package com.senla.converters.community;

import com.senla.converters.post.PostToPostForUserAndCommunityDtoConverter;
import com.senla.converters.user.UserToUserNestedDtoConverter;
import com.senla.dto.community.CommunityDto;
import com.senla.entity.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CommunityDTOConverter implements Converter<Community, CommunityDto> {

    private final UserToUserNestedDtoConverter userToUserNestedDtoConverter;
    private final PostToPostForUserAndCommunityDtoConverter postToPostForUserAndCommunityDtoConverter;

    @Autowired
    public CommunityDTOConverter(UserToUserNestedDtoConverter userToUserNestedDtoConverter, PostToPostForUserAndCommunityDtoConverter postToPostForUserAndCommunityDtoConverter) {
        this.userToUserNestedDtoConverter = userToUserNestedDtoConverter;
        this.postToPostForUserAndCommunityDtoConverter = postToPostForUserAndCommunityDtoConverter;
    }

    @Override
    public CommunityDto convert(Community community) {
        return CommunityDto.builder()
                .id(community.getId())
                .adminUser(userToUserNestedDtoConverter.convert(community.getAdminUser()))
                .name(community.getName())
                .posts(community.getPosts().stream().map(p -> postToPostForUserAndCommunityDtoConverter.convert(p)).collect(Collectors.toList()))
                .users(community.getUsers().stream().map(u -> userToUserNestedDtoConverter.convert(u)).collect(Collectors.toList()))
                .build();
    }
}

