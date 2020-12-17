package com.senla.service.like;

import com.senla.entity.*;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.LikeRepository;
import com.senla.service.post.PostService;
import com.senla.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
        log.info("Adding a like");
        likeRepository.save(like);
        return like;
    }

    @Override
    public void deleteLike(long id) {
        Like like = getLike(id);
        User user = like.getUser();
        user.setLikes(user.getLikes().stream().filter(l -> !l.getId().equals(id)).collect(Collectors.toList()));
        userService.updateUser(user);
        Post post = like.getPost();
        post.setLikes(post.getLikes().stream().filter(l -> !l.getId().equals(id)).collect(Collectors.toList()));
        postService.updatePost(post);
        log.info("Deleting like");
        likeRepository.deleteById(id);
    }

    @Override
    public List<Like> getAll() {
        return likeRepository.findAll();
    }


    @Override
    public Like getLike(Long id) {
        log.info("Getting like by id");
        return likeRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Like with id = %s is not found", id)));
    }

    @Override
    public List<Like> getLikesByPost_Id(Long postId) {
        postService.getPost(postId);
        log.info("Getting like by post");
        List<Like> likes = likeRepository.getLikesByPost_Id(postId);
        if (likes.isEmpty()) {
            log.warn("No likes found under the post");
            throw new EntityNotFoundException("No likes found under the post");
        }
        return likes;
    }
}
