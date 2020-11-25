package com.senla.service;

import com.senla.entity.Post;

import java.util.List;

public interface PostService {
    Post addPost(Post post);
    void deletePost(long id);
    List<Post> getPostsByUser_Id(Long userId);
    Post editPost(Post post);
    List<Post> getAllPosts();
    Post getPost(Long id);
}
