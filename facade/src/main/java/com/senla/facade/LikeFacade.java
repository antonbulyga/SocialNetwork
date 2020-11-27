package com.senla.facade;

import com.senla.converters.LikeDtoToLike;
import com.senla.converters.LikeToLikeDto;
import com.senla.dto.LikeDto;
import com.senla.entity.Like;
import com.senla.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LikeFacade {

    private final LikeService likeService;
    private final LikeToLikeDto likeToLikeDto;
    private final LikeDtoToLike likeDtoToLike;

    @Autowired
    public LikeFacade(LikeService likeService, LikeToLikeDto likeToLikeDto, LikeDtoToLike likeDtoToLike) {
        this.likeService = likeService;
        this.likeToLikeDto = likeToLikeDto;
        this.likeDtoToLike = likeDtoToLike;
    }

    public LikeDto addLike(LikeDto likeDto){
        likeService.addLike(likeDtoToLike.convert(likeDto));
        return likeDto;
    }

    public void deleteLike(long id){
        likeService.deleteLike(id);
    }

    public List<LikeDto> getAllLikes(){
        List<Like> likes = likeService.getAll();
        return likes.stream().map(l -> likeToLikeDto.convert(l)).collect(Collectors.toList());
    }

    public LikeDto getLikeDto(Long id){
        return likeToLikeDto.convert(likeService.getLike(id));
    }

    public Like getLike(Long id){
        return likeService.getLike(id);
    }

    public List<LikeDto> getLikesByPost_Id(Long postId){
        List<Like> likes = likeService.getLikesByPost_Id(postId);
        return likes.stream().map(l -> likeToLikeDto.convert(l)).collect(Collectors.toList());
    }

    public List<LikeDto> convertListLikesToLikeDto(List<Like> likes){
        return likes.stream().map(l -> likeToLikeDto.convert(l)).collect(Collectors.toList());
    }
}
