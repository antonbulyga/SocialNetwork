package com.senla.repository;

import com.senla.entity.Post;
import com.senla.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = ConfigurationTest.class)
@EnableAutoConfiguration
public class PostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void findAll_Success() {
        Post post = new Post();
        post.setText("test post");
        entityManager.persist(post);
        entityManager.flush();
        List<Post> result = postRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findById_Success() {
        Post post = new Post();
        post.setText("test post");
        entityManager.persist(post);
        entityManager.flush();
        Post result = postRepository.findById(post.getId()).orElse(null);
        assertEquals(post, result);
    }

    @Test
    public void addPost_Success() {
        Post post = new Post();
        post.setText("test post");
        entityManager.persist(post);
        entityManager.flush();
        Post result = postRepository.save(post);
        assertEquals(post, result);
    }

    @Test
    public void deletePost_Success() {
        Post post = new Post();
        post.setText("test post");
        entityManager.persist(post);
        entityManager.flush();
        postRepository.deleteById(post.getId());
        Post result = postRepository.findById(post.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    public void updatePost_Success() {
        Post post = new Post();
        post.setText("test post");
        entityManager.persist(post);
        entityManager.flush();
        Post result = postRepository.save(post);
        assertEquals(post, result);
    }

    @Test
    public void getPostByUser_Id_Success() {
        User user = new User();
        user.setUserName("Nikolai");
        user.setCreationTime(LocalDateTime.now());
        entityManager.persist(user);
        Post post = new Post();
        post.setText("test post");
        post.setUser(user);
        entityManager.persist(post);
        entityManager.flush();
        List<Post> posts = postRepository.getPostByUser_Id(user.getId());
        assertEquals(1, posts.size());
    }
}
