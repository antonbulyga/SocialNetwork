package com.senla.converters.post;

import com.senla.dto.post.PostDto;
import com.senla.entity.Community;
import com.senla.entity.Post;
import com.senla.service.community.CommunityService;
import com.senla.service.like.LikeService;
import com.senla.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReversePostDTOConverter implements Converter<PostDto, Post> {

    private final CommunityService communityService;
    private final UserService userService;

    @Autowired
    public ReversePostDTOConverter(CommunityService communityService, UserService userService) {
        this.communityService = communityService;
        this.userService = userService;
    }

    @Override
    public Post convert(PostDto postDto) {
        Community community = null;
        return Post.builder()
                .id(postDto.getId())
                .text(postDto.getText())
                .community(postDto.getCommunity() == null ? community : communityService.getCommunity(postDto.getCommunity().getId()))
                .user(userService.getUser(postDto.getUser().getId()))
                .dateOfCreation(LocalDateTime.now())
                .countLike(postDto.getCountLike())
                .build();
    }
}
