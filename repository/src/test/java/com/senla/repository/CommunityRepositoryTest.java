package com.senla.repository;


import com.senla.entity.Community;
import com.senla.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
public class CommunityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommunityRepository communityRepository;


    @Test
    public void findByCommunityName_Success() {
        Community community = new Community();
        community.setName("Group test");
        entityManager.persist(community);
        entityManager.flush();
        Community found = communityRepository.getCommunityByName(community.getName());
        assertEquals(found.getName(), community.getName());
    }

    @Test
    public void getCommunitiesByAdminUser_Id_Success() {
        Community community = new Community();
        community.setName("Group test");
        User user = new User();
        user.setUserName("Anton");
        user.setCreationTime(LocalDateTime.now());
        entityManager.persist(user);
        entityManager.flush();
        community.setAdminUser(user);
        entityManager.persist(community);
        entityManager.flush();
        List<Community> communities = communityRepository.getCommunitiesByAdminUser_Id(1L);
        assertEquals(1, communities.size());
    }

    @Test
    public void findAll_Success() {
        Community community = new Community();
        community.setName("test dialog 1");
        entityManager.persist(community);
        entityManager.flush();
        List<Community> result = communityRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findById_Success() {
        Community community = new Community();
        community.setName("test dialog 1");
        entityManager.persist(community);
        entityManager.flush();
        Community result = communityRepository.findById(community.getId()).orElse(null);
        assertEquals(community, result);
    }

    @Test
    public void addMessage_Success() {
        Community community = new Community();
        community.setName("test dialog 1");
        entityManager.persist(community);
        entityManager.flush();
        Community result = communityRepository.save(community);
        assertEquals(community, result);
    }

    @Test
    public void deleteMessage_Success() {
        Community community = new Community();
        community.setName("test dialog 1");
        entityManager.persist(community);
        entityManager.flush();
        communityRepository.deleteById(community.getId());
        Community result = communityRepository.findById(community.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    public void updateMessage_Success() {
        Community community = new Community();
        community.setName("test dialog 1");
        entityManager.persist(community);
        entityManager.flush();
        Community result = communityRepository.save(community);
        assertEquals(community, result);
    }
}
