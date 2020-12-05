package com.senla.service.like;

import com.senla.entity.Like;
import com.senla.entity.Post;

import java.util.List;

public interface LikeService {
    Like addLike(Like like);

    void deleteLike(long id);

    List<Like> getAll();

    Like getLike(Long id);

    List<Like> getLikesByPost_Id(Long postId);
}
