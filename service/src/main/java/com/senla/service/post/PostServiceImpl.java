package com.senla.service.post;

import com.senla.entity.Community;
import com.senla.entity.Post;
import com.senla.entity.User;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.PostRepository;
import com.senla.service.community.CommunityService;
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
        log.info("Getting post by id");
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Post with id = %s is not found", id)));
    }

    @Override
    public Post addPost(Post post) {
        log.info("Adding post");
        postRepository.save(post);
        return post;
    }

    @Override
    public void deletePost(long id) {
        Post post = getPost(id);
        Community community = post.getCommunity();
        if (community != null) {
            community.setPosts(community.getPosts().stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList()));
            communityService.updateCommunity(community);
        }
        User user = post.getUser();
        user.setPosts(user.getPosts().stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList()));
        userService.updateUser(user);
        log.info("Deleting post by id");
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getPostsByUser_Id(Long userId) {
        List<Post> posts = postRepository.getPostByUser_Id(userId);
        if (posts.isEmpty()) {
            log.warn("This user has not created any posts");
            throw new EntityNotFoundException("This user has not created any posts");
        }
        return posts;
    }

    @Override
    public Post updatePost(Post post) {
        log.info("Updating post");
        postRepository.save(post);
        return post;
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            log.warn("Post list is empty");
            throw new EntityNotFoundException("Post list is empty");
        }
        return posts;
    }
}
