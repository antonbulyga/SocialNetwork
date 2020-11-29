package com.senla.repository;


import com.senla.entity.Community;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class CommunityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommunityRepository communityRepository;


       @Test
        public void findByCommunityName_Success() {
            entityManager.merge(new Community(1L, "Любители математики" , 1L));
            Community community = this.communityRepository.getCommunityByName("Любители математики");
            assertEquals(community.getName(),"Любители математики");
        }

        @Test
        public void getCommunitiesByAdminUser_Id_Success() {
            entityManager.merge(new Community(1L, "Любители математики" , 1L));
            List<Community> communities = this.communityRepository.getCommunitiesByAdminUser_Id(1L);
            assertEquals(1,communities.size());
        }

}
