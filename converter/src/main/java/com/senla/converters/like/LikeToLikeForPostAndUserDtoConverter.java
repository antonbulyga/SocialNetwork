package com.senla.converters.like;

import com.senla.converters.user.UserToUserNestedDtoConverter;
import com.senla.dto.like.LikeForPostAndUserDto;
import com.senla.entity.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LikeToLikeForPostAndUserDtoConverter implements Converter<Like, LikeForPostAndUserDto> {

    private final UserToUserNestedDtoConverter userToUserNestedDtoConverter;

    @Autowired
    public LikeToLikeForPostAndUserDtoConverter(UserToUserNestedDtoConverter userToUserNestedDtoConverter) {
        this.userToUserNestedDtoConverter = userToUserNestedDtoConverter;
    }

    @Override
    public LikeForPostAndUserDto convert(Like like) {
        return LikeForPostAndUserDto.builder()
                .id(like.getId())
                .userNestedDto(userToUserNestedDtoConverter.convert(like.getUser()))
                .build();
    }
}
