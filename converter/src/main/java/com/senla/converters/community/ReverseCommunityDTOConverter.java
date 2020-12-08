package com.senla.converters.community;

import com.senla.dto.community.CommunityDto;
import com.senla.entity.Community;
import com.senla.service.post.PostService;
import com.senla.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReverseCommunityDTOConverter implements Converter<CommunityDto, Community> {

    private UserService userService;
    private PostService postService;

    @Autowired
    public ReverseCommunityDTOConverter(UserService userService, PostService postService) {
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
                .users(communityDto.getUsers().stream().map(u -> userService.getUser(u.getId())).collect(Collectors.toList()))
                .build();
    }
}
