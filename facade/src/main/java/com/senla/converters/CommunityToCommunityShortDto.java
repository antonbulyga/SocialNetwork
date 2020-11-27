package com.senla.converters;

import com.senla.dto.CommunityShortDto;
import com.senla.entity.Community;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommunityToCommunityShortDto implements Converter<Community, CommunityShortDto> {
    @Override
    public CommunityShortDto convert(Community community) {
        return CommunityShortDto.builder()
                .id(community.getId())
                .name(community.getName())
                .build();
    }
}
