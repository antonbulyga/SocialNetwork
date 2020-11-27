package com.senla.facade;

import com.senla.converters.PostDtoToPost;
import com.senla.converters.PostToPostDto;
import com.senla.dto.PostDto;
import com.senla.entity.Post;
import com.senla.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostFacade {

    private final PostService postService;
    private final PostToPostDto postToPostDto;
    private final PostDtoToPost postDtoToPost;

    @Autowired
    public PostFacade(PostService postService, PostToPostDto postToPostDto, PostDtoToPost postDtoToPost) {
        this.postService = postService;
        this.postToPostDto = postToPostDto;
        this.postDtoToPost = postDtoToPost;
    }

    public PostDto addPost(PostDto postDto){
        postService.addPost(postDtoToPost.convert(postDto));
        return postDto;
    }

    public void deletePost(long id){
        postService.deletePost(id);
    }

    public PostDto updatePost(PostDto postDto){
        Post post = postDtoToPost.convert(postDto);
        postService.updatePost(post);
        return postDto;
    }

    public List<PostDto> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        return posts.stream().map(p -> postToPostDto.convert(p)).collect(Collectors.toList());
    }

    public PostDto getPost(Long id){
        return postToPostDto.convert(postService.getPost(id));
    }

    public List<PostDto> getPostsDtoByUser_Id(Long userId){
        List<Post> posts = postService.getPostsByUser_Id(userId);
        return posts.stream().map(p -> postToPostDto.convert(p)).collect(Collectors.toList());
    }

    public List<Post> getPostsByUser_Id(Long userId){
        List<Post> posts = postService.getPostsByUser_Id(userId);
        return posts;
    }
}