package com.senla.model.service.impl;

import com.senla.model.entity.Post;
import com.senla.model.exception.EntityNotFoundException;
import com.senla.model.repository.PostRepository;
import com.senla.model.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Post with id = %s is not found", id)));
    }

    @Override
    public Post addPost(Post post) {
        postRepository.save(post);
        return post;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getPostsByUser_Id(Long userId) {
        List<Post> posts = postRepository.getPostByUser_Id(userId);
        return posts;
    }

    @Override
    public Post editPost(Post post) {
        postRepository.save(post);
        return post;
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }
}
