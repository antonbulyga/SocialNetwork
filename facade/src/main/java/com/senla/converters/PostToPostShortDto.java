package com.senla.converters;

import com.senla.dto.PostShortDto;
import com.senla.entity.Post;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostToPostShortDto implements Converter<Post, PostShortDto> {

    @Override
    public PostShortDto convert(Post post) {
        return PostShortDto.builder()
                .id(post.getId())
                .text(post.getText())
                .dateOfCreation(post.getDateOfCreation())
                .build();
    }
}
