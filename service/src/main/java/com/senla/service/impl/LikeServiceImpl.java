package com.senla.service.impl;

import com.senla.entity.*;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.LikeRepository;
import com.senla.service.LikeService;
import com.senla.service.PostService;
import com.senla.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;


@Service
@Transactional
@Slf4j
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public Like addLike(Like like) {
        likeRepository.save(like);
        return like;
    }

    @Override
    public void deleteLike(long id) {
        getLike(id);
        Like like = getLike(id);
        User user = like.getUser();
        List<Like> userLikeList = user.getLikes();
        for (Iterator<Like> iter = userLikeList.iterator(); iter.hasNext();) {
            Like currentLike = iter.next();
            if(currentLike.getId() == like.getId()){
                iter.remove();
            }
        }
        user.setLikes(userLikeList);
        userService.updateUser(user);
        Post post = like.getPost();
        List<Like> likeListInPost = post.getLikes();
        for (Iterator<Like> iter = likeListInPost.iterator(); iter.hasNext();) {
            Like currentLike = iter.next();
            if(currentLike.getId() == like.getId()){
                iter.remove();
            }
        }
        post.setLikes(likeListInPost);
        postService.updatePost(post);
        likeRepository.deleteById(id);
    }

    @Override
    public List<Like> getAll() {
        List<Like> likes = likeRepository.findAll();
        return likes;
    }


    @Override
    public Like getLike(Long id) {
        return likeRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Like with id = %s is not found", id)));
    }

    @Override
    public List<Like> getLikesByPost_Id(Long postId) {
        List<Like> likes = likeRepository.getLikesByPost_Id(postId);
        return likes;
    }
}
