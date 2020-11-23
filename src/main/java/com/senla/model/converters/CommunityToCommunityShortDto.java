package com.senla.model.converters;

import com.senla.model.dto.CommunityShortDto;
import com.senla.model.entity.Community;
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
