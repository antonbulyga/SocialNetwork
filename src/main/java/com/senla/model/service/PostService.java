package com.senla.model.service;

import com.senla.model.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {
    Post addPost(Post post);
    void deletePost(long id);
    List<Post> getPostsByUser_Id(Long userId);
    Post editPost(Post post);
    List<Post> getAllPosts();
    Post getPost(Long id);
}
