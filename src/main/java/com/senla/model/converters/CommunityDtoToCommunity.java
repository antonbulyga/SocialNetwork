package com.senla.model.converters;

import com.senla.model.dto.CommunityDto;
import com.senla.model.entity.Community;
import com.senla.model.service.PostService;
import com.senla.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CommunityDtoToCommunity implements Converter<CommunityDto, Community> {
    private UserService userService;
    private PostService postService;

    @Autowired
    public CommunityDtoToCommunity(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public Community convert(CommunityDto communityDto) {
        return Community.builder()
                .id(communityDto.getId())
                .adminUser(userService.getUser(communityDto.getAdminUser().getId()))
                .name(communityDto.getName())
                .posts(communityDto.getPosts().stream().map(p -> postService.getPost(p.getId())).collect(Collectors.toList()))
                .users(communityDto.getUsers().stream().map(u -> userService.getUser(u.getId())).collect(Collectors.toSet()))
                .build();
    }
}
