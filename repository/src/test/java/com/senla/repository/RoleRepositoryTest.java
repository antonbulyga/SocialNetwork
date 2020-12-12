package com.senla.repository;

import com.senla.entity.Role;
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
public class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findAll_Success() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        entityManager.persist(role);
        entityManager.flush();
        List<Role> result = roleRepository.findAll();
        assertEquals(1,result.size());
    }

    @Test
    public void findById_Success() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        entityManager.persist(role);
        entityManager.flush();
        Role result = roleRepository.findById(role.getId()).orElse(null);
        assertEquals(role, result);
    }

    @Test
    public void addRole_Success() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        entityManager.persist(role);
        entityManager.flush();
        Role result = roleRepository.save(role);
        assertEquals(role, result);
    }

    @Test
    public void deleteUser_Success() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        entityManager.persist(role);
        entityManager.flush();
        roleRepository.deleteById(role.getId());
        Role result = roleRepository.findById(role.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    public void updateUser_Success() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        entityManager.persist(role);
        entityManager.flush();
        Role result = roleRepository.save(role);
        assertEquals(role, result);
    }

    @Test
    public void getRoleByName_Success() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        entityManager.persist(role);
        entityManager.flush();
        Role result = roleRepository.getRoleByName(role.getName());
        assertEquals(role, result);
    }
}
