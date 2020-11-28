package com.senla.converters;

import com.senla.dto.PostDto;
import com.senla.entity.Post;
import com.senla.service.CommunityService;
import com.senla.service.LikeService;
import com.senla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PostDtoToPost implements Converter<PostDto, Post> {

    private CommunityService communityService;
    private LikeService likeService;
    private UserService userService;

    @Autowired
    public PostDtoToPost(CommunityService communityService, LikeService likeService, UserService userService) {
        this.communityService = communityService;
        this.likeService = likeService;
        this.userService = userService;
    }

    @Override
    public Post convert(PostDto postDto) {
        return Post.builder()
                .id(postDto.getId())
                .text(postDto.getText())
                .community(communityService.getCommunity(postDto.getCommunity().getId()))
                .likes(postDto.getLikes().stream().map(l -> likeService.getLike(l.getId())).collect(Collectors.toList()))
                .user(userService.getUser(postDto.getUser().getId()))
                .dateOfCreation(postDto.getDateOfCreation())
                .build();
    }
}
