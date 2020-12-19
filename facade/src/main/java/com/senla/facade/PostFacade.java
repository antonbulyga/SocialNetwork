package com.senla.facade;

import com.senla.converters.post.ReversePostDTOConverter;
import com.senla.converters.post.PostDTOConverter;
import com.senla.dto.post.PostDto;
import com.senla.entity.Post;
import com.senla.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostFacade {

    private final PostService postService;
    private final PostDTOConverter postDTOConverter;
    private final ReversePostDTOConverter reversePostDTOConverter;

    @Autowired
    public PostFacade(PostService postService, PostDTOConverter postDTOConverter, ReversePostDTOConverter reversePostDTOConverter) {
        this.postService = postService;
        this.postDTOConverter = postDTOConverter;
        this.reversePostDTOConverter = reversePostDTOConverter;
    }

    public PostDto addPost(PostDto postDto) {
        Post postWithDate = postService.addPost(reversePostDTOConverter.convert(postDto));
        return postDTOConverter.convert(postWithDate);
    }

    public void deletePost(long id) {
        postService.deletePost(id);
    }

    public PostDto updatePost(PostDto postDto) {
        Post postWithDate = postService.updatePost(reversePostDTOConverter.convert(postDto));
        return postDTOConverter.convert(postWithDate);
    }

    public List<PostDto> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return posts.stream().map(postDTOConverter::convert).collect(Collectors.toList());
    }

    public PostDto getPost(Long id) {
        return postDTOConverter.convert(postService.getPost(id));
    }

    public List<PostDto> getPostsDtoByUser_Id(Long userId) {
        List<Post> posts = postService.getPostsByUser_Id(userId);
        return posts.stream().map(postDTOConverter::convert).collect(Collectors.toList());
    }

    public List<PostDto> getPostDtoByCommunity_Id(Long communityId) {
        List<Post> posts = postService.getPostByCommunity_Id(communityId);
        return posts.stream().map(postDTOConverter::convert).collect(Collectors.toList());
    }
}
