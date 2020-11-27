package com.senla.service.impl;

import com.senla.entity.Post;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.PostRepository;
import com.senla.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

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
        getPost(id);
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getPostsByUser_Id(Long userId) {
        return postRepository.getPostByUser_Id(userId);
    }

    @Override
    public Post updatePost(Post post) {
        postRepository.save(post);
        return post;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
