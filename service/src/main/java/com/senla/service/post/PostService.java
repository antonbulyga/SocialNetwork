package com.senla.service.post;

import com.senla.entity.Post;

import java.util.List;

public interface PostService {
    Post addPost(Post post);

    void deletePost(long id);

    List<Post> getPostsByUser_Id(Long userId);

    Post updatePost(Post post);

    List<Post> getAllPosts();

    Post getPost(Long id);

    List<Post> getPostByCommunity_Id(Long communityId);
}
