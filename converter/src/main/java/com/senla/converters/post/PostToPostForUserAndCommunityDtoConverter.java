package com.senla.converters.post;

import com.senla.dto.post.PostForUserAndCommunityDto;
import com.senla.entity.Post;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostToPostForUserAndCommunityDtoConverter implements Converter<Post, PostForUserAndCommunityDto> {

    @Override
    public PostForUserAndCommunityDto convert(Post post) {
        return PostForUserAndCommunityDto.builder()
                .id(post.getId())
                .text(post.getText())
                .dateOfCreation(post.getDateOfCreation())
                .build();
    }
}
