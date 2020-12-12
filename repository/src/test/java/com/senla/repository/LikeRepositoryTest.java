package com.senla.repository;

import com.senla.entity.Like;
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
public class LikeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikeRepository likeRepository;

    @Test
    public void findAll_Success() {
        Like like = new Like();
        User user = new User();
        user.setUserName("Anton");
        user.setCreationTime(LocalDateTime.now());
        entityManager.persist(user);
        like.setUser(user);
        entityManager.persist(like);
        entityManager.flush();
        List<Like> result = likeRepository.findAll();
        assertEquals(1,result.size());
    }

    @Test
    public void findById_Success() {
        Like like = new Like();
        entityManager.persist(like);
        entityManager.flush();
        Like result = likeRepository.findById(like.getId()).orElse(null);
        assertEquals(like, result);
    }

    @Test
    public void addMessage_Success() {
        Like like = new Like();
        entityManager.persist(like);
        entityManager.flush();
        Like result = likeRepository.save(like);
        assertEquals(like, result);
    }

    @Test
    public void deleteMessage_Success() {
        Like like = new Like();
        entityManager.persist(like);
        entityManager.flush();
        likeRepository.deleteById(like.getId());
        Like result = likeRepository.findById(like.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    public void updateMessage_Success() {
        Like like = new Like();
        entityManager.persist(like);
        entityManager.flush();
        Like result = likeRepository.save(like);
        assertEquals(like, result);
    }

    @Test
    public void getLikesByPost_Id_Success() {
        Like like = new Like();
        Post post = new Post();
        post.setText("Test post");
        entityManager.persist(post);
        like.setPost(post);
        entityManager.persist(like);
        entityManager.flush();
        List<Like> result = likeRepository.getLikesByPost_Id(post.getId());
        assertEquals(1, result.size());
    }

}
