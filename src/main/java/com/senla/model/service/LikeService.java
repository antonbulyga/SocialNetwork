package com.senla.model.service;

import com.senla.model.entity.Like;
import com.senla.model.entity.Post;

import java.util.List;

public interface LikeService {
    Like addLike(Like like);
    void delete(long id);
    List<Like> getAll();
    Like getLike(Long id);
    List<Like> getLikesByPost_Id(Long postId);
}
