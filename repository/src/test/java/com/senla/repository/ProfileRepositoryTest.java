package com.senla.repository;

import com.senla.entity.Profile;
import com.senla.enumeration.Gender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = ConfigurationTest.class)
@EnableAutoConfiguration
public class ProfileRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    public void findAll_Success() {
        Profile profile = new Profile();
        profile.setFirstName("Anton");
        entityManager.persist(profile);
        entityManager.flush();
        List<Profile> result = profileRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findById_Success() {
        Profile profile = new Profile();
        profile.setFirstName("Anton");
        entityManager.persist(profile);
        entityManager.flush();
        Profile result = profileRepository.findById(profile.getId()).orElse(null);
        assertEquals(profile, result);
    }

    @Test
    public void addProfile_Success() {
        Profile profile = new Profile();
        profile.setFirstName("Anton");
        entityManager.persist(profile);
        entityManager.flush();
        Profile result = profileRepository.save(profile);
        assertEquals(profile, result);
    }

    @Test
    public void deleteProfile_Success() {
        Profile profile = new Profile();
        profile.setFirstName("Anton");
        entityManager.persist(profile);
        entityManager.flush();
        profileRepository.deleteById(profile.getId());
        Profile result = profileRepository.findById(profile.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    public void updateProfile_Success() {
        Profile profile = new Profile();
        profile.setFirstName("Anton");
        entityManager.persist(profile);
        entityManager.flush();
        Profile result = profileRepository.save(profile);
        assertEquals(profile, result);
    }


    @Test
    public void findByCity_Success() {
        Profile profile = new Profile();
        profile.setFirstName("Anton");
        profile.setCity("Minsk");
        entityManager.persist(profile);
        entityManager.flush();
        List<Profile> result = profileRepository.findByCity(profile.getCity());
        assertEquals(1, result.size());
    }

    @Test
    public void findByFirstName_Success() {
        Profile profile = new Profile();
        profile.setFirstName("Anton");
        entityManager.persist(profile);
        entityManager.flush();
        List<Profile> result = profileRepository.findByFirstName(profile.getFirstName());
        assertEquals(1, result.size());
    }

    @Test
    public void findByLastName_Success() {
        Profile profile = new Profile();
        profile.setLastName("Bulyha");
        entityManager.persist(profile);
        entityManager.flush();
        List<Profile> result = profileRepository.findByLastName(profile.getLastName());
        assertEquals(1, result.size());
    }

    @Test
    public void findByFirstNameAndLastName_Success() {
        Profile profile = new Profile();
        profile.setFirstName("Anton");
        profile.setLastName("Bulyha");
        entityManager.persist(profile);
        entityManager.flush();
        List<Profile> result = profileRepository.findByFirstNameAndLastName(profile.getFirstName(), profile.getLastName());
        assertEquals(1, result.size());
    }

    @Test
    public void findByGender_Success() {
        Profile profile = new Profile();
        profile.setGender(Gender.FEMALE);
        entityManager.persist(profile);
        entityManager.flush();
        List<Profile> result = profileRepository.findByGender(profile.getGender());
        assertEquals(1, result.size());
    }
}
