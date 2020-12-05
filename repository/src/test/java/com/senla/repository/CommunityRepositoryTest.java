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
           Community community = new Community(1L, "Group test");
           entityManager.merge(community);
           entityManager.flush();
           Community found = communityRepository.getCommunityByName(community.getName());
           assertEquals(found.getName(),community.getName());
        }

        @Test
        public void getCommunitiesByAdminUser_Id_Success() {
            Community community = new Community(1L, "Group test");
            User user = new User(1L, "anton@mail.ru", "Anton", "1234" , LocalDateTime.now());
            community.setAdminUser(user);
            entityManager.merge(community);
            List<Community> communities = communityRepository.getCommunitiesByAdminUser_Id(1L);
            assertEquals(1,communities.size());
        }

}
