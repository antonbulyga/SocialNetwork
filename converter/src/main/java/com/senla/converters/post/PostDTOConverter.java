
package com.senla.converters.post;

import com.senla.converters.community.CommunityToCommunityForUserDtoConverter;
import com.senla.converters.user.UserToUserNestedDtoConverter;
import com.senla.dto.community.CommunityForUserDto;
import com.senla.dto.post.PostDto;
import com.senla.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostDTOConverter implements Converter<Post, PostDto> {

    private final CommunityToCommunityForUserDtoConverter communityToCommunityForUserDtoConverter;
    private final UserToUserNestedDtoConverter userToUserNestedDtoConverter;

    @Autowired
    public PostDTOConverter(CommunityToCommunityForUserDtoConverter communityToCommunityForUserDtoConverter,
                            UserToUserNestedDtoConverter userToUserNestedDtoConverter) {
        this.communityToCommunityForUserDtoConverter = communityToCommunityForUserDtoConverter;
        this.userToUserNestedDtoConverter = userToUserNestedDtoConverter;
    }

    @Override
    public PostDto convert(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .text(post.getText())
                .community(post.getCommunity() == null ? null : communityToCommunityForUserDtoConverter.convert(post.getCommunity()))
                .user(userToUserNestedDtoConverter.convert(post.getUser()))
                .dateOfCreation(post.getDateOfCreation())
                .countLike(post.getCountLike())
                .build();
    }
}

