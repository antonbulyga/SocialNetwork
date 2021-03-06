package com.senla.repository;

import com.senla.entity.Message;
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
public class MessageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void findAll_Success() {
        Message message = new Message();
        message.setMessage("test 1");
        entityManager.persist(message);
        entityManager.flush();
        List<Message> result = messageRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findById_Success() {
        Message message = new Message();
        message.setMessage("test 1");
        entityManager.persist(message);
        entityManager.flush();
        Message result = messageRepository.findById(message.getId()).orElse(null);
        assertEquals(message, result);
    }

    @Test
    public void addMessage_Success() {
        Message message = new Message();
        message.setMessage("test 1");
        entityManager.persist(message);
        entityManager.flush();
        Message result = messageRepository.save(message);
        assertEquals(message, result);
    }

    @Test
    public void deleteMessage_Success() {
        Message message = new Message();
        message.setMessage("test 1");
        entityManager.persist(message);
        entityManager.flush();
        messageRepository.deleteById(message.getId());
        Message result = messageRepository.findById(message.getId()).orElse(null);
        assertEquals(null, result);
    }

    @Test
    public void updateMessage_Success() {
        Message message = new Message();
        message.setMessage("test 1");
        entityManager.persist(message);
        entityManager.flush();
        Message result = messageRepository.save(message);
        assertEquals(message, result);
    }
}
