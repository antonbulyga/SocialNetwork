package com.senla.model.converters;

import com.senla.model.dto.LikeDto;
import com.senla.model.entity.Like;
import com.senla.model.service.PostService;
import com.senla.model.service.UserService;
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
