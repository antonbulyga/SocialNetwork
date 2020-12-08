
package com.senla.converters.post;

import com.senla.converters.user.UserToUserNestedDtoConverter;
import com.senla.converters.community.CommunityToCommunityForUserDtoConverter;
import com.senla.converters.like.LikeToLikeForPostAndUserDtoConverter;
import com.senla.dto.community.CommunityForUserDto;
import com.senla.dto.post.PostDto;
import com.senla.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostDTOConverter implements Converter<Post, PostDto> {

    private CommunityToCommunityForUserDtoConverter communityToCommunityForUserDtoConverter;
    private UserToUserNestedDtoConverter userToUserNestedDtoConverter;
    private LikeToLikeForPostAndUserDtoConverter likeToLikeForPostAndUserDtoConverter;

    @Autowired
    public PostDTOConverter(CommunityToCommunityForUserDtoConverter communityToCommunityForUserDtoConverter,
                            UserToUserNestedDtoConverter userToUserNestedDtoConverter, LikeToLikeForPostAndUserDtoConverter likeToLikeForPostAndUserDtoConverter) {
        this.communityToCommunityForUserDtoConverter = communityToCommunityForUserDtoConverter;
        this.userToUserNestedDtoConverter = userToUserNestedDtoConverter;
        this.likeToLikeForPostAndUserDtoConverter = likeToLikeForPostAndUserDtoConverter;
    }

    @Override
    public PostDto convert(Post post) {
        CommunityForUserDto community = null;
        return PostDto.builder()
                .id(post.getId())
                .text(post.getText())
                .community(post.getCommunity() == null ? community : communityToCommunityForUserDtoConverter.convert(post.getCommunity()))
                .user(userToUserNestedDtoConverter.convert(post.getUser()))
                .dateOfCreation(post.getDateOfCreation())
                .countLike(post.getCountLike())
                .build();
    }
}

