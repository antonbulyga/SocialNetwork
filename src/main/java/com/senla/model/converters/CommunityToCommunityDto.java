
package com.senla.model.converters;

import com.senla.model.dto.CommunityDto;
import com.senla.model.entity.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CommunityToCommunityDto implements Converter<Community, CommunityDto> {
    private UserToUserShortDto userToUserShortDto;
    private PostToPostShortDto postToPostShortDto;

    @Autowired
    public CommunityToCommunityDto(UserToUserShortDto userToUserShortDto, PostToPostShortDto postToPostShortDto) {
        this.userToUserShortDto = userToUserShortDto;
        this.postToPostShortDto = postToPostShortDto;
    }

    @Override
    public CommunityDto convert(Community community) {
        return CommunityDto.builder()
                .id(community.getId())
                .adminUser(userToUserShortDto.convert(community.getAdminUser()))
                .name(community.getName())
                .posts(community.getPosts().stream().map(p -> postToPostShortDto.convert(p)).collect(Collectors.toList()))
                .users(community.getUsers().stream().map(u -> userToUserShortDto.convert(u)).collect(Collectors.toList()))
                .build();
    }
}

