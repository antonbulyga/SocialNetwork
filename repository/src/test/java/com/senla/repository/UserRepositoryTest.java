package com.senla.repository;

import com.senla.entity.Profile;
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
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAll_Success() {
        User user = new User();
        user.setUserName("Anton");
        user.setCreationTime(LocalDateTime.now());
        entityManager.persist(user);
        entityManager.flush();
        List<User> result = userRepository.findAll();
        assertEquals(1,result.size());
    }

    @Test
    public void findById_Success() {
        User user = new User();
        user.setUserName("Anton");
        user.setCreationTime(LocalDateTime.now());
        entityManager.persist(user);
        entityManager.flush();
        User result = userRepository.findById(user.getId()).orElse(null);
        assertEquals(user, result);
    }

    @Test
    public void addUser_Success() {
        User user = new User();
        user.setUserName("Anton");
        user.setCreationTime(LocalDateTime.now());
        entityManager.persist(user);
        entityManager.flush();
        User result = userRepository.save(user);
        assertEquals(user, result);
    }

    @Test
    public void deleteUser_Success() {
        User user = new User();
        user.setUserName("Anton");
        user.setCreationTime(LocalDateTime.now());
        entityManager.persist(user);
        entityManager.flush();
        userRepository.deleteById(user.getId());
        User result = userRepository.findById(user.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    public void updateUser_Success() {
        User user = new User();
        user.setUserName("Anton");
        user.setCreationTime(LocalDateTime.now());
        entityManager.persist(user);
        entityManager.flush();
        User result = userRepository.save(user);
        assertEquals(user, result);
    }

    @Test
    public void findUserByEmail_Success() {
        User user = new User();
        user.setUserName("Anton");
        user.setCreationTime(LocalDateTime.now());
        user.setEmail("ant@mail.ru");
        entityManager.persist(user);
        entityManager.flush();
        User result = userRepository.findByEmail(user.getEmail());
        assertEquals(user, result);
    }

    @Test
    public void findByUserName_Success() {
        User user = new User();
        user.setUserName("Anton");
        user.setCreationTime(LocalDateTime.now());
        entityManager.persist(user);
        entityManager.flush();
        User result = userRepository.findByUserName(user.getUserName());
        assertEquals(user, result);
    }
}
