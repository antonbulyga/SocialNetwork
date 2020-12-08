package com.senla.converters.community;

import com.senla.dto.community.CommunityForUserDto;
import com.senla.entity.Community;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommunityToCommunityForUserDtoConverter implements Converter<Community, CommunityForUserDto> {

    @Override
    public CommunityForUserDto convert(Community community) {
        return CommunityForUserDto.builder()
                .id(community.getId())
                .name(community.getName())
                .build();
    }
}
