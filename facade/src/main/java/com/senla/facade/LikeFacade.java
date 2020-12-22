package com.senla.facade;

import com.senla.converters.like.ReverseLikeDTOConverter;
import com.senla.converters.like.LikeDTOConverter;
import com.senla.dto.like.LikeDto;
import com.senla.entity.Like;
import com.senla.entity.Post;
import com.senla.entity.User;
import com.senla.service.like.LikeService;
import com.senla.service.post.PostService;
import com.senla.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LikeFacade {

    private final LikeService likeService;
    private final LikeDTOConverter likeDTOConverter;
    private final ReverseLikeDTOConverter reverseLikeDTOConverter;
    private final PostService postService;

    @Autowired
    public LikeFacade(LikeService likeService, LikeDTOConverter likeDTOConverter, ReverseLikeDTOConverter reverseLikeDTOConverter, PostService postService) {
        this.likeService = likeService;
        this.likeDTOConverter = likeDTOConverter;
        this.reverseLikeDTOConverter = reverseLikeDTOConverter;
        this.postService = postService;
    }

    public int likeFromUserUnderPostCheck(Long postId, Long userId) {
        Post postFromDto = postService.getPost(postId);
        List<Like> likes = postFromDto.getLikes();
        int count = 0;
        for (Like l : likes) {
            if (l.getUser().getId().equals(userId)) {
                count++;
            }
        }
        return count;
    }

    public LikeDto addLike(LikeDto likeDto) {
        Like like = likeService.addLike(reverseLikeDTOConverter.convert(likeDto));
        return likeDTOConverter.convert(like);
    }

    public void deleteLike(Long id) {
        likeService.deleteLike(id);
    }

    public List<LikeDto> getAllLikes() {
        List<Like> likes = likeService.getAll();
        return likes.stream().map(likeDTOConverter::convert).collect(Collectors.toList());
    }

    public LikeDto getLikeDto(Long id) {
        return likeDTOConverter.convert(likeService.getLike(id));
    }

    public Like getLike(Long id) {
        return likeService.getLike(id);
    }

    public List<LikeDto> getLikesByPost_Id(Long postId) {
        List<Like> likes = likeService.getLikesByPost_Id(postId);
        return likes.stream().map(likeDTOConverter::convert).collect(Collectors.toList());
    }

    public List<LikeDto> convertListLikesToLikeDto(List<Like> likes) {
        return likes.stream().map(likeDTOConverter::convert).collect(Collectors.toList());
    }
}
