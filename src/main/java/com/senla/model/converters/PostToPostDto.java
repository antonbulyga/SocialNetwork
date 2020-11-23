
package com.senla.model.converters;

import com.senla.model.dto.CommunityShortDto;
import com.senla.model.dto.PostDto;
import com.senla.model.entity.Like;
import com.senla.model.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostToPostDto implements Converter<Post, PostDto> {
    private CommunityToCommunityShortDto communityToCommunityShortDto;
    private UserToUserShortDto userToUserShortDto;
    private LikeToLikeShortDto likeToLikeShortDto;

    @Autowired
    public PostToPostDto(CommunityToCommunityShortDto communityToCommunityShortDto,
                         UserToUserShortDto userToUserShortDto, LikeToLikeShortDto likeToLikeShortDto) {
        this.communityToCommunityShortDto = communityToCommunityShortDto;
        this.userToUserShortDto = userToUserShortDto;
        this.likeToLikeShortDto = likeToLikeShortDto;
    }

    @Override
    public PostDto convert(Post post) {
        CommunityShortDto community = null;
        return PostDto.builder()
                .id(post.getId())
                .text(post.getText())
                .community(post.getCommunity() == null ? community : communityToCommunityShortDto.convert(post.getCommunity()))
                .user(userToUserShortDto.convert(post.getUser()))
                .likes(post.getLikes().stream().map(l -> likeToLikeShortDto.convert(l)).collect(Collectors.toList()))
                .dateOfCreation(post.getDateOfCreation())
                .build();
    }
}

