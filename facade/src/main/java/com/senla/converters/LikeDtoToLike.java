package com.senla.converters;

import com.senla.dto.LikeDto;
import com.senla.entity.Like;
import com.senla.service.PostService;
import com.senla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LikeDtoToLike implements Converter<LikeDto, Like> {

    private UserService userService;
    private PostService postService;

    @Autowired
    public LikeDtoToLike(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public Like convert(LikeDto likeDto) {
        return Like.builder()
                .id(likeDto.getId())
                .user(userService.getUser(likeDto.getUser().getId()))
                .post(postService.getPost(likeDto.getPost().getId()))
                .build();
    }
}
