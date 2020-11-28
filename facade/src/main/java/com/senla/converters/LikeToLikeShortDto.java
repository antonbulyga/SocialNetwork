package com.senla.converters;

import com.senla.dto.LikeShortDto;
import com.senla.entity.Like;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LikeToLikeShortDto implements Converter<Like, LikeShortDto> {

    @Override
    public LikeShortDto convert(Like like) {
        return LikeShortDto.builder()
                .id(like.getId())
                .build();
    }
}
