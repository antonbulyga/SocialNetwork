package com.senla.repository;

import com.senla.entity.Dialog;
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
public class DialogRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DialogRepository dialogRepository;

    @Test
    public void findByDialogName_Success() {
        Dialog dialog = new Dialog();
        dialog.setName("Test 1");
        dialog.setTimeCreation(LocalDateTime.now());
        entityManager.persist(dialog);
        entityManager.flush();
        Dialog found = dialogRepository.getDialogByName(dialog.getName());
        assertEquals(found.getName(), dialog.getName());
    }

    @Test
    public void findAll_Success() {
        Dialog dialog = new Dialog();
        dialog.setName("test dialog 1");
        entityManager.persist(dialog);
        entityManager.flush();
        List<Dialog> result = dialogRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findById_Success() {
        Dialog dialog = new Dialog();
        dialog.setName("test dialog 1");
        entityManager.persist(dialog);
        entityManager.flush();
        Dialog result = dialogRepository.findById(dialog.getId()).orElse(null);
        assertEquals(dialog, result);
    }

    @Test
    public void addMessage_Success() {
        Dialog dialog = new Dialog();
        dialog.setName("test dialog 1");
        entityManager.persist(dialog);
        entityManager.flush();
        Dialog result = dialogRepository.save(dialog);
        assertEquals(dialog, result);
    }

    @Test
    public void deleteMessage_Success() {
        Dialog dialog = new Dialog();
        dialog.setName("test dialog 1");
        entityManager.persist(dialog);
        entityManager.flush();
        dialogRepository.deleteById(dialog.getId());
        Dialog result = dialogRepository.findById(dialog.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    public void updateMessage_Success() {
        Dialog dialog = new Dialog();
        dialog.setName("test dialog 1");
        entityManager.persist(dialog);
        entityManager.flush();
        Dialog result = dialogRepository.save(dialog);
        assertEquals(dialog, result);
    }
}
