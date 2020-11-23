
package com.senla.model.converters;

import com.senla.model.dto.LikeDto;
import com.senla.model.entity.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LikeToLikeDto implements Converter<Like, LikeDto> {
    private PostToPostShortDto postToPostShortDto;
    private UserToUserShortDto userToUserShortDto;

    @Autowired
    public LikeToLikeDto(PostToPostShortDto postToPostShortDto, UserToUserShortDto userToUserShortDto) {
        this.postToPostShortDto = postToPostShortDto;
        this.userToUserShortDto = userToUserShortDto;
    }

    @Override
    public LikeDto convert(Like like) {
        return LikeDto.builder()
                .id(like.getId())
                .post(postToPostShortDto.convert(like.getPost()))
                .user(userToUserShortDto.convert(like.getUser()))
                .build();
    }
}

