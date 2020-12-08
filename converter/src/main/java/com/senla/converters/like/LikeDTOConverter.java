
package com.senla.converters.like;

import com.senla.converters.post.PostToPostForUserAndCommunityDtoConverter;
import com.senla.converters.user.UserToUserNestedDtoConverter;
import com.senla.dto.like.LikeDto;
import com.senla.entity.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LikeDTOConverter implements Converter<Like, LikeDto> {

    private PostToPostForUserAndCommunityDtoConverter postToPostForUserAndCommunityDtoConverter;
    private UserToUserNestedDtoConverter userToUserNestedDtoConverter;

    @Autowired
    public LikeDTOConverter(PostToPostForUserAndCommunityDtoConverter postToPostForUserAndCommunityDtoConverter, UserToUserNestedDtoConverter userToUserNestedDtoConverter) {
        this.postToPostForUserAndCommunityDtoConverter = postToPostForUserAndCommunityDtoConverter;
        this.userToUserNestedDtoConverter = userToUserNestedDtoConverter;
    }

    @Override
    public LikeDto convert(Like like) {
        return LikeDto.builder()
                .id(like.getId())
                .post(postToPostForUserAndCommunityDtoConverter.convert(like.getPost()))
                .user(userToUserNestedDtoConverter.convert(like.getUser()))
                .build();
    }
}

