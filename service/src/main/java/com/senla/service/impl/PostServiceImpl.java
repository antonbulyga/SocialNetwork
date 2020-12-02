package com.senla.service.impl;

import com.senla.entity.Community;
import com.senla.entity.Post;
import com.senla.entity.User;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.PostRepository;
import com.senla.service.CommunityService;
import com.senla.service.PostService;
import com.senla.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommunityService communityService;
    private final UserService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CommunityService communityService, UserService userService) {
        this.postRepository = postRepository;
        this.communityService = communityService;
        this.userService = userService;
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
        Post post = getPost(id);
        Community community = post.getCommunity();
        if(community != null) {
            community.setPosts(community.getPosts().stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList()));
            communityService.updateCommunity(community);
        }
        User user = post.getUser();
        user.setPosts(user.getPosts().stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList()));
        userService.updateUser(user);
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
